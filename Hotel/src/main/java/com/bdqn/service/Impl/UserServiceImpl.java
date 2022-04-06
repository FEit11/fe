package com.bdqn.service.Impl;

import com.bdqn.dao.UserMapper;
import com.bdqn.entity.User;
import com.bdqn.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param user
     * @return
     */
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    /**
     * 根据用户名查询用户信息接口实现
     * @param loginName
     * @return
     */
    public User findUserByName(String loginName) {
        return userMapper.findUserByName(loginName);
    }

    /**
     * 用户登录接口实现
     * @param loginName
     * @param password
     * @return
     */
    public User login(String loginName, String password) {
        //调用查询用户信息的方法
        User userByName = userMapper.findUserByName(loginName);
        //判断对象是否为空
        if(userByName != null){
            if(userByName.getPassword().equals(password)){
                return userByName;
            }
        }
        return null;
    }
}
