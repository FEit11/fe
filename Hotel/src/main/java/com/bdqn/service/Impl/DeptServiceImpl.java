package com.bdqn.service.Impl;

import com.bdqn.dao.DeptMapper;
import com.bdqn.entity.Dept;
import com.bdqn.service.DeptService;
import com.bdqn.vo.DeptVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 按页查询部门列表
     * @param deptVo
     * @return
     */
    public List<Dept> findDeptList(DeptVo deptVo) {
        return deptMapper.findDeptList(deptVo);
    }

    /**
     * 添加部门接口实现
     * @param dept
     * @return
     */
    public int addDept(Dept dept) {
        //设置创建时间
        dept.setCreateDate(new Date());
        return deptMapper.addDept(dept);
    }

    /**
     * 编辑部门接口实现
     * @param dept
     * @return
     */
    public int editDept(Dept dept) {
        return deptMapper.editDept(dept);
    }

    /**
     * 删除部门接口实现
     * @param id
     * @return
     */
    public int deleteById(Integer id) {

        return deptMapper.deleteById(id);
    }

    /**
     * 获取所有部门接口实现
     */
    public List<Dept> findDeptAllList() {
        return deptMapper.findDeptAllList();
    }
}
