package com.feidea.dao;


import com.feidea.entity.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendLinkMapper {

    //查询所有友链
    List<FriendLink> listFriendLink();

    //友链新增
    int saveFriendLink(FriendLink friendLink);

    //根据网址查询友链
    FriendLink getFriendLinkByBlogaddress(String blogaddress);

    //编辑友链
    int updateFriendLink(FriendLink friendLink);

    //通过id获取友链
    FriendLink getFriendLink(Long id);

    //删除友链
    void deleteFriendLink(Long id);
}
