package com.bdqn.controller;

import com.bdqn.entity.Menu;
import com.bdqn.entity.Role;
import com.bdqn.service.EmployeeService;
import com.bdqn.service.MenuService;
import com.bdqn.service.RoleService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.TreeNode;
import com.bdqn.vo.RoleVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private MenuService menuService;

    /**
     *
     * @param roleVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(RoleVo roleVo){
        //使用Mybatis的分页插件PageHelper
        Page<Role> pages = PageHelper.startPage(roleVo.getPage(),roleVo.getLimit());
        //查询数据
        List<Role> roles = roleService.findRoleList(roleVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Role> deptResultData = new ResultData<Role>();
        //设置deptResultData中的属性以满足layui接收数据格式
        deptResultData.setCode(0);
        deptResultData.setMsg("");
        deptResultData.setCount(pages.getTotal());
        deptResultData.setData(roles);
        return deptResultData;
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    public String addDept(Role role){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加部门的方法
        if(roleService.addRole(role) > 0){
            //添加成功
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else {
            //添加失败
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑部门
     * @param role
     * @return
     */
    @RequestMapping("/editRole")
    public String editDept(Role role){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加部门的方法
        if(roleService.EditRole(role) > 0){
            //添加成功
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else {
            //添加失败
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 判断该角色下是否存在员工
     * @param id
     * @return
     */
    @RequestMapping("/checkEmployeeByRoleId")
    public String checkEmployeeByRoleId(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用删除部门的方法
        if(employeeService.getEmployeeCountByRoleId(id) > 0){
            map.put(SystemConstant.EXIST,true);//表示该角色下存在员工
            map.put(SystemConstant.MESSAGE,"该部门存在员工，无法删除");
        }else {
            map.put(SystemConstant.EXIST,false);//表示该角色下不存在员工
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除该角色
     * @param id
     * @return
     */
    @RequestMapping("/deleteByRoleId")
    public String deleteByRoleId(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用删除部门的方法
        if(roleService.deleteByRoleId(id) > 0){
            map.put(SystemConstant.SUCCESS,true);//删除成功
            map.put(SystemConstant.MESSAGE,"删除成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);//删除失败
            map.put(SystemConstant.MESSAGE,"删除失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 初始化分配菜单树
     * @return
     */
    @RequestMapping("/initMenuTree")
    public ResultData initMenuTree(Integer roleId) {
        //调用查询菜单列表的方法
        List<Menu> menuList = menuService.findMenuList();
        //调用根据角色编号查询已有的菜单编号的方法
        List<Integer> menuIdListByRoleIds = menuService.findMenuIdListByRoleId(roleId);
        //定义集合，保存菜单信息
        List<Menu> menuList1 = new ArrayList<Menu>();
        //判断集合是否存在数据
        if(menuIdListByRoleIds != null && menuIdListByRoleIds.size() > 0){
            //根据菜单Id查询该菜单信息
            menuList1 = menuService.findMenuByMenuId(menuIdListByRoleIds);
        }

        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        //循环遍历所有菜单
        for(Menu menu : menuList){

            String checkArr = "0";//复选框状态，0不选，1选中
            //内层循环遍历当前角色所有的权限
            for(Menu currentMenu : menuList1){
                //如果Id相等，表示当前角色有这个权限，分配菜单时复选框中默认选上
                if(menu.getId() == currentMenu.getId()){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (menu.getSpread() == null || menu.getSpread() == 1) ? true : false;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),spread,checkArr));
        }
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<TreeNode> deptResultData = new ResultData<TreeNode>();
        //设置deptResultData中的属性以满足layui接收数据格式
        deptResultData.setCode(0);
        deptResultData.setMsg("");
        deptResultData.setData(treeNodes);
        return deptResultData;
    }

    /**
     * 分配菜单
     * @param ids
     * @param roleId
     * @return
     */
    @RequestMapping("/saveRoleMenu")
    public String saveRoleMenu(String ids,Integer roleId){
        Map<String,Object> map = new HashMap<String, Object>();

        if(roleService.saveRoleMenu(ids,roleId) > 0){
            map.put("message","权限分配成功");
        }else {
            map.put("message","权限分配失败");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/initRoleList")
    public ResultData initRoleList(int id){
        //调用查询所有角色列表的方法
        List<Map<String, Object>> roleListMap = roleService.findRoleListMap();
        //调用根据员工Id查询所拥有角色方法
        List<Integer> allEmployeeHasRole = roleService.findAllEmployeeHasRole(id);
        //创建ResultData对象保存所需信息
        ResultData resultData = new ResultData();
        //设置对象以满足layuimini所需要的数据格式
        resultData.setCode(0);
        resultData.setMsg("");
        for (Map<String, Object> map : roleListMap) {
            //判断复选框是否选中
            boolean flag = false;
            //获取每一个角色的Id
            Integer id1 = (Integer) map.get("id");
            //比较两个集合中的角色id是否相同
            for (Integer integer : allEmployeeHasRole) {
                if(integer == id1){
                    flag = true;
                    break;
                }
            }
            map.put("LAY_CHECKED",flag);
        }
        resultData.setCount(Long.valueOf(roleListMap.size()));
        resultData.setData(roleListMap);
        return resultData;
    }

}
