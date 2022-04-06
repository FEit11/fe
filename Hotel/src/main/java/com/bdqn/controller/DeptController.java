package com.bdqn.controller;


import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Dept;
import com.bdqn.service.DeptService;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.DeptVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/dept")
public class DeptController {



    @Resource
    private DeptService deptService;

    @Resource
    private EmployeeService employeeService;


    /**
     * 查询部门列表
     * @param deptVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(DeptVo deptVo){
        //使用分页插件PageHelper
        Page<Dept> pages = PageHelper.startPage(deptVo.getPage(),deptVo.getLimit());
        //查询数据
        List<Dept> depts = deptService.findDeptList(deptVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Dept> deptResultData = new ResultData<Dept>();
        //设置deptResultData中的属性以满足layui接收数据格式
        deptResultData.setCode(0);
        deptResultData.setMsg("");
        deptResultData.setCount(pages.getTotal());
        deptResultData.setData(depts);
        return deptResultData;
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @RequestMapping("/addDept")
    public String addDept(Dept dept){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加部门的方法
        if(deptService.addDept(dept) > 0){
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
     * @param dept
     * @return
     */
    @RequestMapping("/editDept")
    public String editDept(Dept dept){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加部门的方法
        if(deptService.editDept(dept) > 0){
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
     * 判断该部门是否存在员工
     * @param id
     * @return
     */
    @RequestMapping("/checkEmployeeByDeptId")
    public String checkEmployeeByDeptId(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用根据部门id查询员工数量的方法判断能否删除该部门
        if(employeeService.getEmployeeCountByDeptId(id) > 0){
            map.put(SystemConstant.EXIST,true);//表示该部门存在员工
            map.put(SystemConstant.MESSAGE,"该部门存在员工，无法删除");
        }else {
            map.put(SystemConstant.EXIST,false);//表示该部门不存在员工
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("/deleteByDeptId")
    public String deleteByDeptId(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用删除部门的方法
        if(deptService.deleteById(id) > 0){
            map.put(SystemConstant.SUCCESS,true);//删除成功
            map.put(SystemConstant.MESSAGE,"删除成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);//删除失败
            map.put(SystemConstant.MESSAGE,"删除失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查询所有部门
     * @return
     */
    @RequestMapping("/deptList")
    public String deptList(){
        //调用查询所有部门信息的方法并返回到页面
        return JSON.toJSONString(deptService.findDeptAllList());
    }
}


