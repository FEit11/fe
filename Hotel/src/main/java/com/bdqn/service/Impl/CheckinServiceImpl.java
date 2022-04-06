package com.bdqn.service.Impl;

import com.bdqn.dao.CheckinMapper;
import com.bdqn.dao.OrdersMapper;
import com.bdqn.dao.RoomTypeMapper;
import com.bdqn.entity.Checkin;
import com.bdqn.entity.Orders;
import com.bdqn.entity.RoomType;
import com.bdqn.service.CheckinService;
import com.bdqn.vo.CheckinVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CheckinServiceImpl implements CheckinService {

    @Resource
    private CheckinMapper checkinMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RoomTypeMapper roomTypeMapper;

    /**
     * 查询入住信息列表接口实现
     * @param checkinVo
     * @return
     */
    public List<Checkin> findCheckinList(CheckinVo checkinVo) {
        return checkinMapper.findCheckinList(checkinVo);
    }

    /**
     * 登记入住接口实现
     * @param checkin
     * @return
     */
    public int addCheckin(Checkin checkin) {
        checkin.setStatus(1);//设置状态
        checkin.setCreatedate(new Date());//设置入住时间
        int flag = checkinMapper.addCheckin(checkin);
        if(flag > 0){
            //办理入住后,修改订单状态,先获取订单对象
            Orders orders = new Orders();
            orders.setId(checkin.getOrdersid());
            orders.setStatus(3);
            ordersMapper.updateOrders(orders);

            //修改房型表中的已入住数量+1
            RoomType roomType = roomTypeMapper.findById(checkin.getRoomtypeid());
            roomType.setLivednum(roomType.getLivednum() + 1);
            roomTypeMapper.editRoomType(roomType);
        }
        return flag;
    }
}
