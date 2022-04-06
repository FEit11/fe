package com.bdqn.controller;


import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Employee;
import com.bdqn.entity.Menu;
import com.bdqn.service.MenuService;
import com.bdqn.utils.*;
import com.bdqn.vo.MenuVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 加载左侧菜单导航
     * @return
     */
    @RequestMapping("/loadMenuList")
    public  String loadMenuList(HttpSession session){

        //创建Map集合，保存菜单MenuInfo信息
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        //创建Map集合，保存homeInfo信息
        Map<String,Object> home = new LinkedHashMap<String, Object>();
        //创建Map集合，保存logoInfo信息
        Map<String,Object> logo = new LinkedHashMap<String, Object>();
        //调用接口中的方法获取菜单信息
        //List<Menu> menuList = menuService.();该方法无论是哪个角色的用户登录，都能够查询所有的功能模块

        //获取当前登录员工
        Employee employee = (Employee) session.getAttribute(SystemConstant.LOGINUSER);

        //根据当前用户的角色动态显示菜单列表
        List<Menu> menuList = menuService.findMenuListByEmployeeId(employee.getId());

        List<MenuNode> menuNodeList = new ArrayList<MenuNode>();

        for (Menu menu : menuList) {
            MenuNode menuNode = new MenuNode();
            menuNode.setHref(menu.getHref());
            menuNode.setIcon(menu.getIcon());
            menuNode.setId(menu.getId());
            menuNode.setPid(menu.getPid());
            menuNode.setSpread(menu.getSpread());
            menuNode.setTarget(menu.getTarget());
            menuNode.setTitle(menu.getTitle());
            menuNodeList.add(menuNode);
        }
        //保存homeInfo信息
        home.put("title","首页");
        home.put("href","/admin/desktop");
        //保存logoInfo信息
        logo.put("title","酒店管理系统");
        logo.put("image","/statics/layui/images/logo.png");
        logo.put("href","/admin/home.html");
        //将菜单信息添加到MenuInfo集合中
        map.put("menuInfo", TreeUtil.toTree(menuNodeList,0));
        map.put("homeInfo",home);
        map.put("logoInfo",logo);
        return JSON.toJSONString(map);
    }

    /**
     * 加载菜单管理页面的左侧导航树
     * @return
     */
    @RequestMapping("/loadMenuTreeLeft")
    public ResultData loadMenuTreeLeft(){
        //查询所有菜单信息
        List<Menu> menuList = menuService.findMenuList();
        //创建TreeNode保存节点信息
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        //遍历循环菜单列表
        for (Menu menu : menuList) {
            //判断是否菜单展开
            Boolean spread = (menu.getSpread() == null || menu.getSpread() == 1) ? true : false;
            //将菜单信息保存到treeNodes集合中
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),spread));
        }
        //创建集合去满足layui所需数据格式
        ResultData<TreeNode> resultData = new ResultData<TreeNode>();
        resultData.setMsg("");
        resultData.setCode(0);
        resultData.setData(treeNodes);
        return resultData;
    }

    /**
     * 查询菜单列表
     * @param menuVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(MenuVo menuVo){
        //使用分页插件PageHelper
        Page<Menu> pages = PageHelper.startPage(menuVo.getPage(),menuVo.getLimit());
        //查询数据
        List<Menu> menus = menuService.findAllMenuListByPage(menuVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Menu> deptResultData = new ResultData<Menu>();
        //设置deptResultData中的属性以满足layui接收数据格式
        deptResultData.setCode(0);
        deptResultData.setMsg("");
        deptResultData.setCount(pages.getTotal());
        deptResultData.setData(menus);
        return deptResultData;
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    @RequestMapping("/addMenu")
    public String add(Menu menu){
        Map<String,Object> map = new HashMap<String, Object>();
        menu.setIcon("fa "+menu.getIcon());
        if(menuService.addMenu(menu) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加菜单成功");
        }else {
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加菜单失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑菜单
     * @param menu
     * @return
     */
    @RequestMapping("/editMenu")
    public String editMenu(Menu menu){
        Map<String,Object> map = new HashMap<String, Object>();

        if(menuService.EditMenu(menu) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改菜单成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改菜单失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑菜单
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();

        if(menuService.deleteById(id) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"菜单删除成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"菜单删除失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除前判断该菜单是否存在子菜单
     * @param id
     * @return
     */
    @RequestMapping("/checkMenuHasChild")
    public String checkMenuHasChild(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();

        if(menuService.getMenuCountByMenuId(id) > 0){
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"该菜单有子菜单,无法删除");
        }else {
            map.put(SystemConstant.EXIST,false);
        }
        return JSON.toJSONString(map);
    }
}
