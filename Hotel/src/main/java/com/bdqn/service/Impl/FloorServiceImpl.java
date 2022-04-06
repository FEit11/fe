package com.bdqn.service.Impl;

import com.bdqn.dao.FloorMapper;
import com.bdqn.entity.Floor;
import com.bdqn.service.FloorService;
import com.bdqn.vo.FloorVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    @Resource
    private FloorMapper floorMapper;

    /**
     * 查询所有楼层数据接口实现
     * @param floorVo
     * @return
     */
    public List<Floor> findFloorList(FloorVo floorVo){
        return floorMapper.findFloorList(floorVo);
    }

    /**
     * 添加楼层接口实现
     * @param floor
     * @return
     */
    public int addFloor(Floor floor) {
        return floorMapper.addFloor(floor);
    }

    /**
     * 编辑楼层接口实现
     * @param floor
     * @return
     */
    public int editFloor(Floor floor) {
        return floorMapper.editFloor(floor);
    }
}
