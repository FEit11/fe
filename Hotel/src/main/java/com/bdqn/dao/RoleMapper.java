package com.bdqn.dao;

import com.bdqn.entity.Role;
import com.bdqn.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface RoleMapper {

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
     * 添加分配的权限
     * @param roleId
     * @param menuId
     */
    void addRoleMenu(@Param("roleId") Integer roleId,@Param("menuId") String menuId);

    /**
     * 删除原有的权限
     * @param roleId
     */
    void deleteRoleMenu(Integer roleId);

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
