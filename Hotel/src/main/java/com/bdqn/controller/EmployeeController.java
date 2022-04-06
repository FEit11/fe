package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Employee;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.EmployeeVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
        Map<String,Object> map = new HashMap<String, Object>();

        //调用员工登录的方法
        Employee employee = employeeService.login(username, password);

        if(employee != null){

            session.setAttribute(SystemConstant.LOGINUSER,employee);
            map.put(SystemConstant.SUCCESS,true);
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"账号密码错误,登录失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 员工列表
     * @param employeeVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(EmployeeVo employeeVo){
        //使用分页插件PageHelper
        Page<Employee> pages = PageHelper.startPage(employeeVo.getPage(),employeeVo.getLimit());
        //查询数据
        List<Employee> employees = employeeService.findEmployeeList(employeeVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Employee> employeeResultData = new ResultData<Employee>();
        //设置deptResultData中的属性以满足layui接收数据格式
        employeeResultData.setCode(0);
        employeeResultData.setMsg("");
        employeeResultData.setCount(pages.getTotal());
        employeeResultData.setData(employees);
        return employeeResultData;
    }

    /**
     * 添加部门
     * @param employee
     * @param session
     * @return
     */
    @RequestMapping("/addEmployee")
    public String addEmployee(Employee employee,HttpSession session){
        Map<String,Object> map = new HashMap<String, Object>();
        //获取当前登录的用户
        Employee LoginUser = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //设置创建人
        employee.setCreatedBy(LoginUser.getId());
        //调用添加员工的方法
        if(employeeService.addEmployee(employee) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑员工
     * @param employee
     * @param session
     * @return
     */
    @RequestMapping("/editEmployee")
    public String editEmployee(Employee employee,HttpSession session){
        Map<String,Object> map = new HashMap<String, Object>();
        //获取当前登录的用户
        Employee LoginUser = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //设置修改人
        employee.setModifyBy(LoginUser.getId());
        //调用编辑员工的方法
        if(employeeService.editEmployee(employee) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @RequestMapping("/deleteEmployeeById")
    public String deleteEmployeeById(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用删除部门的方法
        if(employeeService.deleteEmployeeById(id) > 0){
            map.put(SystemConstant.SUCCESS,true);//删除成功
            map.put(SystemConstant.MESSAGE,"删除成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);//删除失败
            map.put(SystemConstant.MESSAGE,"删除失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 保存员工分配的角色
     * @param roleIds
     * @param empId
     * @return
     */
    @RequestMapping("/saveEmployeeRole")
    public String saveEmployeeRole(String roleIds,Integer empId){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用保存分配给员工角色的方法
        if(employeeService.saveEmployeeRole(roleIds,empId)){
            map.put(SystemConstant.MESSAGE,"分配成功");
        }else {
            map.put(SystemConstant.MESSAGE,"分配失败");
        }
        return JSON.toJSONString(map);
    }
}
