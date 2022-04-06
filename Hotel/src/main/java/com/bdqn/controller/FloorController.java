package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Floor;
import com.bdqn.service.FloorService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.FloorVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/floor")
public class FloorController {

    @Resource
    private FloorService floorService;

    /**
     * 查询楼层列表
     * @param floorVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(FloorVo floorVo){
        //使用分页插件PageHelper
        Page<Floor> pages = PageHelper.startPage(floorVo.getPage(),floorVo.getLimit());
        //查询数据
        List<Floor> floors = floorService.findFloorList(floorVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Floor> floorResultData = new ResultData<Floor>();
        //设置deptResultData中的属性以满足layui接收数据格式
        floorResultData.setCode(0);
        floorResultData.setMsg("");
        floorResultData.setCount(pages.getTotal());
        floorResultData.setData(floors);
        return floorResultData;
    }

    /**
     * 添加楼层
     * @param floor
     * @return
     */
    @RequestMapping("/addFloor")
    public String addFloor(Floor floor){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加楼层的方法
        if(floorService.addFloor(floor) > 0){
            //添加成功
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else {
            //添加失败
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑楼层
     * @param floor
     * @return
     */
    @RequestMapping("/editFloor")
    public String editFloor(Floor floor){
        Map<String,Object> map = new HashMap<String, Object>();
        //调用添加楼层的方法
        if(floorService.editFloor(floor) > 0){
            //添加成功
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else {
            //添加失败
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/findAll")
    public String findAll(){
        return JSON.toJSONString(floorService.findFloorList(null));
    }
}
