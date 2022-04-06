package com.bdqn.service.Impl;

import com.bdqn.dao.EmployeeMapper;
import com.bdqn.entity.Employee;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.EmployeeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录功能实现
     * @param loginName
     * @param loginPwd
     * @return
     */
    public Employee login(String loginName, String loginPwd) {
        //调用根据账号查询员工信息的方法
        Employee employee = employeeMapper.findEmployeeByLoginName(loginName);
        //
        if(employee != null){


            if(employee.getLoginPwd().equals(loginPwd)){
                return employee;
            }
        }
        return null;
    }

    /**
     * 根据部门编号查询员工数量接口实现
     * @param deptId
     * @return
     */
    public int getEmployeeCountByDeptId(Integer deptId) {
        return employeeMapper.getEmployeeCountByDeptId(deptId);
    }

    /**
     * 根据角色编号查询员工数量接口实现
     * @param roleId
     * @return
     */
    public int getEmployeeCountByRoleId(Integer roleId) {
        return employeeMapper.getEmployeeCountByRoleId(roleId);
    }

    /**
     * 多种条件查询员工接口实现
     * @param employeeVo
     * @return
     */
    public List<Employee> findEmployeeList(EmployeeVo employeeVo) {
        return employeeMapper.findEmployeeList(employeeVo);
    }

    /**
     * 添加员工接口实现
     * @param employee
     * @return
     */
    public int addEmployee(Employee employee) {
        employee.setCreateDate(new Date());
        employee.setLoginPwd(SystemConstant.password);
        return employeeMapper.addEmployee(employee);
    }

    /**
     * 编辑员工接口实现
     * @param employee
     * @return
     */
    public int editEmployee(Employee employee) {
        //设置修改时间
        employee.setModifyDate(new Date());
        return employeeMapper.editEmployee(employee);
    }

    /**
     * 删除员工接口实现
     * @param id
     * @return
     */
    public int deleteEmployeeById(Integer id) {
        //首先删除员工与角色关系
        employeeMapper.deleteEmployeeAndRole(id);
        return employeeMapper.deleteEmployeeById(id);
    }

    /**
     * 先删除原有员工与角色关系
     * 再添加，防止数据冗余以及取消员工的角色
     * @param roleIds
     * @param empId
     * @return
     */
    public boolean saveEmployeeRole(String roleIds, Integer empId) {
        try {
            //首先删除员工与角色关系的数据
            employeeMapper.deleteEmployeeAndRole(empId);
            //然后保存员工新分配的员工与角色关系
            String[] idArr = roleIds.split(",");
            for (int i = 0; i < idArr.length; i++) {
                employeeMapper.addEmployeeRole(idArr[i], empId);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
