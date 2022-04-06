package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Orders;
import com.bdqn.service.OrdersService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.OrdersVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/orders")
public class OrdersAdminController {

    @Resource
    private OrdersService ordersService;


    @RequestMapping("/list")
    public ResultData<Orders> list(OrdersVo ordersVo){
        //使用分页插件PageHelper
        Page<Orders> pages = PageHelper.startPage(ordersVo.getPage(),ordersVo.getLimit());
        //查询数据
        List<Orders> ordersList = ordersService.findOrdersList(ordersVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Orders> ordersResultData = new ResultData<Orders>();
        //设置deptResultData中的属性以满足layui接收数据格式
        ordersResultData.setCode(0);
        ordersResultData.setMsg("");
        ordersResultData.setCount(pages.getTotal());
        ordersResultData.setData(ordersList);
        return ordersResultData;
    }

    @RequestMapping("/confirmOrders")
    public String confirmOrders(Orders orders){
        Map<String,Object> map = new HashMap<String, Object>();
        //确认后设置订单状态
        orders.setStatus(2);

        //调用修改的方法
        if(ordersService.updateOrders(orders) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"订单确认成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"订单确认失败");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/batchConfirm")
    public String batchConfirm(String ids){
        Map<String,Object> map = new HashMap<String, Object>();
        //字符串拆分成数组
        String[] split = ids.split(",");
        int flag = 0;
        for (int i = 0; i < split.length; i++) {

            Orders orders = new Orders();
            orders.setStatus(2);//设置状态
            orders.setId(Integer.valueOf(split[i]));
            flag = ordersService.updateOrders(orders);
            if (flag > 0) {
                map.put(SystemConstant.SUCCESS,true);
                map.put(SystemConstant.MESSAGE,"订单确认成功");
            }
        }
        if (flag <= 0){
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"订单确认失败");
        }
        return JSON.toJSONString(map);
    }

}
