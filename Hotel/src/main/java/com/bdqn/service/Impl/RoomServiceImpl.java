package com.bdqn.service.Impl;

import com.bdqn.dao.RoomMapper;
import com.bdqn.entity.Room;
import com.bdqn.service.RoomService;
import com.bdqn.vo.RoomVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;

    /**
     * 分页查询房间列表接口实现
     * @param roomVo
     * @return
     */
    public List<Room> findRoomListByPage(RoomVo roomVo) {
        return roomMapper.findRoomListByPage(roomVo);
    }

    /**
     * 添加房间接口实现
     * @param room
     * @return
     */
    public int addRoom(Room room) {
        return roomMapper.addRoom(room);
    }

    /**
     * 编辑房间接口实现
     * @param room
     * @return
     */
    public int editRoom(Room room) {
        return roomMapper.editRoom(room);
    }

    /**
     * 删除房间接口实现
     * @param id
     * @return
     */
    public int deleteById(Integer id) {
        return roomMapper.deleteById(id);
    }

    /**
     * 根据楼层查询房间接口实现
     * @return
     */
    public List<Room> findRoomListByFloorId() {
        return roomMapper.findRoomListByFloorId();
    }

    /**
     * 查看房间详情接口实现
     * @param id
     * @return
     */
    public Room findById(Integer id) {
        return roomMapper.findById(id);
    }
}
