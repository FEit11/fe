package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.RoomType;
import com.bdqn.service.RoomTypeService;
import com.bdqn.utils.ResultData;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.UUIDUtils;
import com.bdqn.vo.RoomTypeVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/roomType")
public class RoomTypeController {

    @Resource
    private RoomTypeService roomTypeService;

    /**
     * 获取房型列表
     * @param roomTypeVo
     * @return
     */
    @RequestMapping("/list")
    public ResultData list(RoomTypeVo roomTypeVo){
        //使用分页插件PageHelper
        Page<RoomType> pages = PageHelper.startPage(roomTypeVo.getPage(),roomTypeVo.getLimit());
        //查询数据
        List<RoomType> roomTypes = roomTypeService.findRoomTypeList(roomTypeVo);
        //创建一个ResultData集合存储layuimini所需信息
        ResultData<RoomType> roomTypeResultData = new ResultData<RoomType>();
        //设置deptResultData中的属性以满足layui接收数据格式
        roomTypeResultData.setCode(0);
        roomTypeResultData.setMsg("");
        roomTypeResultData.setCount(pages.getTotal());
        roomTypeResultData.setData(roomTypes);
        return roomTypeResultData;
    }


    /**
     * 添加房型
     * @param roomType
     * @return
     */
    @RequestMapping("/addRoomType")
    public String addRoomType(RoomType roomType){
        Map<String,Object> map = new HashMap<String, Object>();
        if(roomTypeService.addRoomType(roomType) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 编辑房型
     * @param roomType
     * @return
     */
    @RequestMapping("/editRoomType")
    public String editRoomType(RoomType roomType){
        Map<String,Object> map = new HashMap<String, Object>();
        if(roomTypeService.editRoomType(roomType) > 0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else {
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     *
     * @return
     */
    @RequestMapping("/findAll")
    public String findAll(){
        return JSON.toJSONString(roomTypeService.findRoomTypeList(null));
    }
}
