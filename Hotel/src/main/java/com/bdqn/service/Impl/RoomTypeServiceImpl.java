package com.bdqn.service.Impl;

import com.bdqn.dao.RoomTypeMapper;
import com.bdqn.entity.RoomType;
import com.bdqn.service.RoomTypeService;
import com.bdqn.vo.RoomTypeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    @Resource
    private RoomTypeMapper roomTypeMapper;

    /**
     * 查询房型列表接口实现
     * @param roomTypeVo
     * @return
     */
    public List<RoomType> findRoomTypeList(RoomTypeVo roomTypeVo) {
        return roomTypeMapper.findRoomTypeList(roomTypeVo);
    }

    /**
     * 添加房型接口实现
     * @param roomType
     * @return
     */
    public int addRoomType(RoomType roomType) {
        //可用房间数默认是全部房间数
        roomType.setAvilablenum(roomType.getRoomnum());
        roomType.setLivednum(0);
        return roomTypeMapper.addRoomType(roomType);
    }

    /**
     * 编辑房型接口实现
     * @param roomType
     * @return
     */
    public int editRoomType(RoomType roomType) {
        //可用房间数默认是全部房间数
        roomType.setAvilablenum(roomType.getRoomnum());
        roomType.setLivednum(0);
        return roomTypeMapper.editRoomType(roomType);
    }


}
