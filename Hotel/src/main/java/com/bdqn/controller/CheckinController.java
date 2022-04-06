package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Checkin;
import com.bdqn.entity.Employee;
import com.bdqn.service.CheckinService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.CheckinVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/checkin")
public class CheckinController {

    @Resource
    private CheckinService checkinService;

    /**
     * 查询入住信息列表
     * @param checkinVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(CheckinVo checkinVo){
        //使用分页插件PageHelper
        Page<Checkin> pages = PageHelper.startPage(checkinVo.getPage(),checkinVo.getLimit());
        //查询数据
        List<Checkin> depts = checkinService.findCheckinList(checkinVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Checkin> checkinResultData = new ResultData<Checkin>();
        //设置deptResultData中的属性以满足layui接收数据格式
        checkinResultData.setCode(0);
        checkinResultData.setMsg("");
        checkinResultData.setCount(pages.getTotal());
        checkinResultData.setData(depts);
        return checkinResultData;
    }

    /**
     * 登记入住
     * @param checkin
     * @return
     */
    @RequestMapping("/addCheckin")
    public String addCheckin(Checkin checkin, HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        //获取当前登录用户
        Employee employee = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //创建人
        checkin.setCreatedby(employee.getId());
        //调用添加入住信息的方法
        if(checkinService.addCheckin(checkin)>0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"办理入住成功");
        }else{
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"办理入住失败");
        }
        return JSON.toJSONString(map);
    }
}
