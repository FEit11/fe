package com.bdqn.dao;

import com.bdqn.entity.Employee;
import com.bdqn.vo.EmployeeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    /**
     * 提供通过登录用户名查询接口
     * 调用返回一个实体类
     * @param loginName
     * @return
     */
    Employee findEmployeeByLoginName(String loginName);

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
     * 提供各种条件查询
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
     * 员工编辑功能
     * @param employee
     * @return
     */
    int editEmployee(Employee employee);

    /**
     * 删除员工
     * @param id
     * @return
     */
    int deleteEmployeeById(Integer id);

    /**
     * 删除员工与角色的关系
     * @param id
     */
    void deleteEmployeeAndRole(Integer id);

    /**
     * 添加员工与角色的关系
     * @param roleId
     * @param empId
     */
    void addEmployeeRole(@Param("rid") String roleId, @Param("eid") Integer empId);
}
