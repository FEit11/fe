package com.feidea.service.impl;

import com.feidea.dao.UserMapper;
import com.feidea.entity.User;
import com.feidea.service.UserService;
import com.feidea.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务层接口实现
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        if (username == null && password == null) {
            return null;
        }
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
