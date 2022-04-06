package com.bdqn.service;

import com.bdqn.entity.Role;
import com.bdqn.vo.RoleVo;

import java.util.List;
import java.util.Map;

public interface RoleService {

    /**
     * 查询角色数据表
     * @param roleVo
     * @return
     */
    List<Role> findRoleList(RoleVo roleVo);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 编辑角色
     * @param role
     * @return
     */
    int EditRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteByRoleId(Integer id);

    /**
     * 保存角色权限
     * @param ids
     * @param roleId
     * @return
     */
    int saveRoleMenu(String ids, Integer roleId);

    /**
     * 查询所有角色列表
     * @return
     */
    List<Map<String,Object>> findRoleListMap();

    /**
     * 根据员工id查询该员工所拥有的所有角色
     * @param employeeId
     * @return
     */
    List<Integer> findAllEmployeeHasRole(Integer employeeId);
}
