package com.bdqn.service;

import com.bdqn.entity.Orders;
import com.bdqn.vo.OrdersVo;

import java.util.List;

public interface OrdersService {

    /**
     * 生成订单接口
     * @param orders
     * @return
     */
    int addOrders(Orders orders);

    /**
     * 查询预订列表接口
     * @param ordersVo
     * @return
     */
    List<Orders> findOrdersList(OrdersVo ordersVo);

    /**
     * 确认订单接口
     * @param orders
     * @return
     */
    int updateOrders(Orders orders);
}
