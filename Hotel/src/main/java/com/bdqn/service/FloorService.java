package com.bdqn.service;

import com.bdqn.entity.Floor;
import com.bdqn.vo.FloorVo;

import java.util.List;

public interface FloorService {

    /**
     * 查询所有楼层数据接口
     * @param floorVo
     * @return
     */
    List<Floor> findFloorList(FloorVo floorVo);

    /**
     * 添加楼层接口
     * @param floor
     * @return
     */
    int addFloor(Floor floor);

    /**
     * 编辑楼层接口
     * @param floor
     * @return
     */
    int editFloor(Floor floor);
}
