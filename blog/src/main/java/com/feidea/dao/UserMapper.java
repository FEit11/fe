package com.feidea.dao;

import com.feidea.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户登录持久层接口
 */
@Mapper
@Repository
public interface UserMapper {

    //通过用户名和密码查询用户
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
