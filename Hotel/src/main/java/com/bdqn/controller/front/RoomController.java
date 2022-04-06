package com.bdqn.controller.front;

import com.bdqn.entity.Room;
import com.bdqn.entity.RoomType;
import com.bdqn.service.RoomService;
import com.bdqn.service.RoomTypeService;
import com.bdqn.vo.RoomVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Resource
    private RoomService roomService;

    @Resource
    private RoomTypeService roomTypeService;


    // ---路径  /room/1.html

    @RequestMapping("/{id}.html")
    public String detail(@PathVariable Integer id, Model model){
        //调用方法
        Room room = roomService.findById(id);
        //将数据放到模型中，无刷新的才有模型
        model.addAttribute("room",room);
        return "detail";
    }

    /**
     * 查询全部房间列表
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String list(Model model){
        //调用查询所有房型列表的方法
        List<RoomType> roomTypeList = roomTypeService.findRoomTypeList(null);

        RoomVo roomVo = new RoomVo();
        roomVo.setStatus(3);//可预订才显示
        //查询房间列表
        List<Room> roomList = roomService.findRoomListByPage(roomVo);
        model.addAttribute("roomTypeList",roomTypeList);
        model.addAttribute("roomList",roomList);
        return "hotelList";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/list/{id}")
    public String list(@PathVariable Integer id, Model model){
        //调用查询所有房型列表的方法
        List<RoomType> roomTypeList = roomTypeService.findRoomTypeList(null);
        //创建查询条件类
        RoomVo roomVo = new RoomVo();
        roomVo.setRoomtypeid(id);
        //查询房间列表
        List<Room> roomList = roomService.findRoomListByPage(roomVo);
        model.addAttribute("roomTypeList",roomTypeList);
        model.addAttribute("roomList",roomList);
        model.addAttribute("typeId",id);
        return "hotelList";
    }
}
