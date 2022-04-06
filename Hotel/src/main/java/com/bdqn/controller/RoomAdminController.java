package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Room;
import com.bdqn.service.RoomService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.RoomVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/room")
public class RoomAdminController {

    @Resource
    private RoomService roomService;

    @RequestMapping("/list")
    public ResultData list(RoomVo roomVo){
        //使用分页插件PageHelper
        Page<Room> pages = PageHelper.startPage(roomVo.getPage(),roomVo.getLimit());
        //查询数据
        List<Room> rooms = roomService.findRoomListByPage(roomVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<Room> roomResultData = new ResultData<Room>();
        //设置deptResultData中的属性以满足layui接收数据格式
        roomResultData.setCode(0);
        roomResultData.setMsg("");
        roomResultData.setCount(pages.getTotal());
        roomResultData.setData(rooms);
        return roomResultData;
    }


    /**
     * 添加房间
     * @param room
     * @return
     */
    @RequestMapping("/addRoom")
    public String addRoom(Room room){
        Map<String,Object> map = new HashMap<String, Object>();
        if(roomService.addRoom(room) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑房间
     * @param room
     * @return
     */
    @RequestMapping("/editRoom")
    public String editRoom(Room room){
        Map<String,Object> map = new HashMap<String, Object>();
        if(roomService.editRoom(room) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除房间
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        Map<String,Object> map = new HashMap<String, Object>();
        if(roomService.deleteById(id) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"删除成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"删除失败");
        }
        return JSON.toJSONString(map);
    }
}
