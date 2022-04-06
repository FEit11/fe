package com.bdqn.dao;

import com.bdqn.entity.RoomType;
import com.bdqn.vo.RoomTypeVo;

import java.util.List;

public interface RoomTypeMapper {

    /**
     * 查询房型列表
     * @param roomTypeVo
     * @return
     */
    List<RoomType> findRoomTypeList(RoomTypeVo roomTypeVo);

    /**
     * 添加房型
     * @param roomType
     * @return
     */
    int addRoomType(RoomType roomType);

    /**
     * 编辑房型
     * @param roomType
     * @return
     */
    int editRoomType(RoomType roomType);

    /**
     * 根据房型编号查询信息
     * @param roomtypeid
     * @return
     */
    RoomType findById(Integer roomtypeid);
}
