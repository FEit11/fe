package com.bdqn.dao;

import com.bdqn.entity.Menu;
import com.bdqn.vo.MenuVo;

import java.util.*;

public interface MenuMapper {

    /**
     * 查询所有菜单列表
     * @return
     */
    List<Menu> findMenuList();

    /**
     * 根据角色编号去查询有什么菜单编号
     * @param roleId
     * @return
     */
    List<Integer> findMenuIdListByRoleId(int roleId);

    /**
     * 根据菜单编号查询菜单信息
     * @param menuIdListByRoleIds
     * @return
     */
    List<Menu> findMenuByMenuId(List<Integer> menuIdListByRoleIds);

    /**
     * 分页查询菜单列表
     * @param menuVo
     * @return
     */
    List<Menu> findAllMenuListByPage(MenuVo menuVo);

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    int addMenu(Menu menu);

    /**
     * 编辑菜单
     * @param menu
     * @return
     */
    int EditMenu(Menu menu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    int deleteById(int id);

    /**
     * 根据菜单id查询该菜单是否存在子菜单
     * @param id
     * @return
     */
    int getMenuCountByMenuId(Integer id);

    /**
     * 根据当前登录员工的角色查询菜单列表
     * 每个员工分配的管理模块不同
     * @param employeeId
     * @return
     */
    List<Menu> findMenuListByEmployeeId(Integer employeeId);
}
