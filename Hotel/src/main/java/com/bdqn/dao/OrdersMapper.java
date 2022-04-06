package com.bdqn.dao;

import com.bdqn.entity.Orders;
import com.bdqn.vo.OrdersVo;

import java.util.List;

public interface OrdersMapper {

    /**
     * 生成订单
     * @param orders
     * @return
     */
    int addOrders(Orders orders);

    /**
     * 查询预订列表
     * @param ordersVo
     * @return
     */
    List<Orders> findOrdersList(OrdersVo ordersVo);

    /**
     * 确认订单
     * @param orders
     * @return
     */
    int updateOrders(Orders orders);
}
