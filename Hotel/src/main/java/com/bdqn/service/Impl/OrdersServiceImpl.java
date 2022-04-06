package com.bdqn.service.Impl;

import com.bdqn.dao.OrdersMapper;
import com.bdqn.dao.RoomMapper;
import com.bdqn.dao.RoomTypeMapper;
import com.bdqn.entity.Orders;
import com.bdqn.entity.Room;
import com.bdqn.entity.RoomType;
import com.bdqn.service.OrdersService;
import com.bdqn.utils.UUIDUtils;
import com.bdqn.vo.OrdersVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private RoomTypeMapper roomTypeMapper;

    /**
     * 生成订单接口实现
     * @param orders
     * @return
     */
    public int addOrders(Orders orders) {
        orders.setStatus(1);//待确认订单
        orders.setOrdersno(UUIDUtils.randomUUID());
        orders.setReservedate(new Date());//设置预订时间
        int flag = ordersMapper.addOrders(orders);
        if(flag > 0){
            //预订成功后,修改房间状态为已预订
            //查询房间信息
            Room room = roomMapper.findById(orders.getRoomid());
            room.setStatus(1);//设置房间状态为已预订
            //调用修改房间信息的方法
            roomMapper.editRoom(room);

            //修改房型信息,可用房间-1,已预订+1
            RoomType roomType = roomTypeMapper.findById(orders.getRoomtypeid());
            roomType.setAvilablenum(roomType.getAvilablenum() - 1);
            roomType.setReservednum(roomType.getReservednum() + 1);
            roomTypeMapper.editRoomType(roomType);
        }
        return flag;
    }

    /**
     * 查询预订列表接口实现
     * @param ordersVo
     * @return
     */
    public List<Orders> findOrdersList(OrdersVo ordersVo) {

        return ordersMapper.findOrdersList(ordersVo);
    }

    /**
     * 确认订单接口实现
     * @param orders
     * @return
     */
    public int updateOrders(Orders orders) {
        return ordersMapper.updateOrders(orders);
    }
}
