package com.bdqn.service;

import com.bdqn.entity.Room;
import com.bdqn.vo.RoomVo;

import java.util.List;

public interface RoomService {

    /**
     * 分页查询房间列表接口
     * @param roomVo
     * @return
     */
    List<Room> findRoomListByPage(RoomVo roomVo);

    /**
     * 添加房间接口
     * @param room
     * @return
     */
    int addRoom(Room room);

    /**
     * 编辑房间接口
     * @param room
     * @return
     */
    int editRoom(Room room);

    /**
     * 删除房间接口
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据楼层查询房间接口
     * @return
     */
    List<Room> findRoomListByFloorId();

    /**
     * 查看房间详情接口
     * @param id
     * @return
     */
    Room findById(Integer id);
}
