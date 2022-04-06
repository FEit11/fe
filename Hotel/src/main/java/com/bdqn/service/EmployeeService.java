package com.bdqn.service;

import com.bdqn.entity.Employee;
import com.bdqn.vo.EmployeeVo;

import java.util.List;

public interface EmployeeService {


    /**
     * 提供通过登录用户名查询接口
     * 调用返回一个实体类
     * @param loginName
     * @return
     */
    Employee login(String loginName,String loginPwd);

    /**
     * 根据部门Id去查询该部门是否存在员工并返回员工数量
     * 若不存在员工，才可以进行删除操作
     * @param deptId
     * @return
     */
    int getEmployeeCountByDeptId(Integer deptId);

    /**
     * 根据角色编号去查询该部门是否存在员工并返回员工数量
     * 若不存在员工，才可以进行删除操作
     * @param roleId
     * @return
     */
    int getEmployeeCountByRoleId(Integer roleId);

    /**
     * 提供各种条件查询接口
     * 返回员工集合
     * @param employeeVo
     * @return
     */
    List<Employee> findEmployeeList(EmployeeVo employeeVo);

    /**
     * 添加员工接口
     * @param employee
     * @return
     */
    int addEmployee(Employee employee);

    /**
     * 编辑员工接口
     * @param employee
     * @return
     */
    int editEmployee(Employee employee);

    /**
     * 删除员工接口
     * @param id
     * @return
     */
    int deleteEmployeeById(Integer id);

    /**
     * 保存给员工分配的角色
     * @param roleIds
     * @param empId
     * @return
     */
    boolean saveEmployeeRole(String roleIds, Integer empId);
}
