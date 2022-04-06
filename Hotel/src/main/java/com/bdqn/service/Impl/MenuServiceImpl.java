package com.bdqn.service.Impl;

import com.bdqn.dao.MenuMapper;
import com.bdqn.entity.Menu;
import com.bdqn.service.MenuService;
import com.bdqn.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 查询所有菜单列表
     * @return
     */
    public List<Menu> findMenuList() {
        return menuMapper.findMenuList();
    }

    /**
     *
     * @param roleId
     * @return
     */
    public List<Integer> findMenuIdListByRoleId(int roleId) {
        return menuMapper.findMenuIdListByRoleId(roleId);
    }

    public List<Menu> findMenuByMenuId(List<Integer> menuIdListByRoleIds) {
        return menuMapper.findMenuByMenuId(menuIdListByRoleIds);
    }

    /**
     * 分页查询菜单列表接口实现
     * @param menuVo
     * @return
     */
    public List<Menu> findAllMenuListByPage(MenuVo menuVo) {
        return menuMapper.findAllMenuListByPage(menuVo);
    }

    /**
     * 添加菜单接口实现
     * @param menu
     * @return
     */
    public int addMenu(Menu menu) {
        //如果没有父级菜单，默认为0
        if(menu.getPid() == null){
            menu.setPid(0);
        }
        //设置打开方式
        menu.setTarget("_self");
        return menuMapper.addMenu(menu);
    }

    /**
     * 编辑菜单接口实现
     * @param menu
     * @return
     */
    public int EditMenu(Menu menu) {
        return menuMapper.EditMenu(menu);
    }

    /**
     * 删除菜单接口实现
     * @param id
     * @return
     */
    public int deleteById(int id) {
        return menuMapper.deleteById(id);
    }

    /**
     * 根据菜单id查询该菜单是否存在子菜单
     * @param id
     * @return
     */
    public int getMenuCountByMenuId(Integer id) {
        return menuMapper.getMenuCountByMenuId(id);
    }

    /**
     * 接口实现
     * @param employeeId
     * @return
     */
    public List<Menu> findMenuListByEmployeeId(Integer employeeId) {
        return menuMapper.findMenuListByEmployeeId(employeeId);
    }
}
