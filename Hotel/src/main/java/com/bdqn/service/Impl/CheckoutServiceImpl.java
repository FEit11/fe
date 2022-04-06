package com.bdqn.service.Impl;

import com.bdqn.dao.*;
import com.bdqn.entity.*;
import com.bdqn.service.CheckoutService;
import com.bdqn.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
@Transactional
public class CheckoutServiceImpl implements CheckoutService {

    @Resource
    private CheckoutMapper checkoutMapper;

    @Resource
    private CheckinMapper checkinMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RoomTypeMapper roomTypeMapper;

    @Resource
    private RoomMapper roomMapper;

    /**
     * 添加退房记录接口实现
     * @param checkout
     * @return
     */
    public int addCheckout(Checkout checkout) {
        //设置创建退房记录创建时间
        checkout.setCreateDate(new Date());
        //设置退房记录单号
        checkout.setCheckOutNumber(UUIDUtils.randomUUID());

        int flag = checkoutMapper.addCheckout(checkout);

        if(flag > 0){
            //改变入住登记中的状态
            Checkin checkin = checkinMapper.findById(checkout.getCheckInId());
            checkin.setStatus(2);
            checkinMapper.updateCheckin(checkin);

            //修改订单的状态
            Orders orders = new Orders();
            orders.setStatus(4);
            orders.setId(checkin.getOrdersid());
            ordersMapper.updateOrders(orders);

            //修改房型的数据
            RoomType roomType = roomTypeMapper.findById(checkin.getRoomtypeid());
            roomType.setAvilablenum(roomType.getAvilablenum() + 1);
            roomType.setLivednum(roomType.getLivednum() - 1);
            roomTypeMapper.editRoomType(roomType);

            //修改房间状态
            Room room = new Room();
            room.setStatus(3);//修改该房间号为可预订
            Long a = checkin.getRoomid();
            room.setId(a.intValue());
            roomMapper.editRoom(room);
        }

        return flag;
    }
}
