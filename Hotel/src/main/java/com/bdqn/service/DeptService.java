package com.bdqn.service;

import com.bdqn.entity.Dept;
import com.bdqn.utils.ResultData;
import com.bdqn.vo.DeptVo;

import java.util.List;

public interface DeptService {
    /**
     * 查询部门列表
     * @param deptVo
     * @return
     */
    List<Dept> findDeptList(DeptVo deptVo);

    /**
     * 添加部门
     * @param dept
     * @return
     */
    int addDept(Dept dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    int editDept(Dept dept);

    /**
     * 删除部门
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 获取所有部门
     * @return
     */
    List<Dept> findDeptAllList();
}
