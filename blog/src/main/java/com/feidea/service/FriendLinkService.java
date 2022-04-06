package com.feidea.service;

import java.util.List;
import com.feidea.entity.FriendLink;

public interface FriendLinkService {

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
