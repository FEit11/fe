package com.feidea.service;

import com.feidea.entity.User;
import org.springframework.stereotype.Service;

/**
 * 用户业务层登录接口
 */

public interface UserService {

    //检查是否存在该用户
    User checkUser(String username, String password);
}
