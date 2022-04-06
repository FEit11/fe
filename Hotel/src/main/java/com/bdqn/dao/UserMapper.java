package com.bdqn.dao;

import com.bdqn.entity.User;

public interface UserMapper {

    /**
     * 用户注册
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据用户名查询用户信息
     * @param loginName
     * @return
     */
    User findUserByName(String loginName);

}
