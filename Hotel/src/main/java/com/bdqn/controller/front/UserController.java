package com.bdqn.controller.front;


import com.alibaba.fastjson.JSON;
import com.bdqn.entity.User;
import com.bdqn.service.UserService;
import com.bdqn.utils.SystemConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.bdqn.utils.SystemConstant.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public String register(User user){
        //创建Map集合，保存结果
        Map<String,Object> map = new HashMap<String, Object>();
        //调用注册方法
        if(userService.addUser(user) > 0){
            map.put(SUCCESS,true);
            map.put(MESSAGE,"注册成功!");
        }else {
            map.put(SUCCESS,false);
            map.put(MESSAGE,"注册失败!请重新操作");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 检查用户名是否重复
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkName")
    public String checkName(String loginName){
        //创建Map集合，保存结果
        Map<String,Object> map = new HashMap<String, Object>();
        //调用方法
        if(userService.findUserByName(loginName) != null){
            map.put(EXIST,true);
            map.put(MESSAGE,"用户名已存在!");
        }else {
            map.put(EXIST,false);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 用户登录
     * @param loginName
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        //创建Map集合，保存结果
        Map<String,Object> map = new HashMap<String, Object>();
        //调用登录方法
        User loginUser = userService.login(loginName, password);
        //判断对象是否为空
        if(loginUser != null){
            map.put(SUCCESS,true);
            loginUser.setPassword(null);//清空
            session.setAttribute(FRONT_LOGINUSER,loginUser);//保存登录的用户
        }else {
            map.put(SUCCESS,false);
            map.put(MESSAGE,"用户名或密码错误,请重新操作");
        }
        return JSON.toJSONString(map);
    }
}
