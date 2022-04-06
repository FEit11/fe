package com.bdqn.service.Impl;

import com.bdqn.dao.RoleMapper;
import com.bdqn.entity.Role;
import com.bdqn.service.RoleService;
import com.bdqn.vo.RoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 按页查询查询角色数据
     * @param roleVo
     * @return
     */
    public List<Role> findRoleList(RoleVo roleVo) {
        return roleMapper.findRoleList(roleVo);
    }

    /**
     * 添加角色接口实现
     * @param role
     * @return
     */
    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }

    /**
     * 编辑角色接口实现
     * @param role
     * @return
     */
    public int EditRole(Role role) {
        return roleMapper.EditRole(role);
    }

    /**
     * 删除角色接口实现
     * @param id
     * @return
     */
    public int deleteByRoleId(Integer id) {
        return roleMapper.deleteByRoleId(id);
    }

    /**
     * 保存分配权限
     * @param ids
     * @param roleId
     * @return
     */
    public int saveRoleMenu(String ids, Integer roleId) {
        try {
            //删除角色原有权限
            roleMapper.deleteRoleMenu(roleId);
            //将字符串转换成数组
            String[] idsStr = ids.split(",");
            //循环调用添加权限
            for (int i = 0; i < idsStr.length; i++) {
                //添加分配的权限
                roleMapper.addRoleMenu(roleId, idsStr[i]);
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询所有角色列表
     * @return
     */
    public List<Map<String, Object>> findRoleListMap() {
        return roleMapper.findRoleListMap();
    }

    /**
     * 根据员工id查询所有的角色
     * @param employeeId
     * @return
     */
    public List<Integer> findAllEmployeeHasRole(Integer employeeId) {
        return roleMapper.findAllEmployeeHasRole(employeeId);
    }
}
