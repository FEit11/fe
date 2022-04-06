package com.bdqn.service;

import com.bdqn.entity.RoomType;
import com.bdqn.vo.RoomTypeVo;

import java.util.List;

public interface RoomTypeService {

    /**
     * 查询房型列表接口
     * @param roomTypeVo
     * @return
     */
    List<RoomType> findRoomTypeList(RoomTypeVo roomTypeVo);

    /**
     * 添加房型接口
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
}
