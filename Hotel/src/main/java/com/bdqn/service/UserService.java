package com.bdqn.service;

import com.bdqn.entity.User;

public interface UserService {

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据用户名查询用户信息接口
     * @param loginName
     * @return
     */
    User findUserByName(String loginName);

    /**
     * 用户登录接口
     * @param loginName
     * @param password
     * @return
     */
    User login(String loginName,String password);
}
