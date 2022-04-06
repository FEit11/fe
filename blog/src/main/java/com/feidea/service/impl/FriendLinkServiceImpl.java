package com.feidea.service.impl;

import com.alibaba.fastjson.JSON;
import com.feidea.dao.FriendLinkMapper;
import com.feidea.entity.FriendLink;
import com.feidea.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<FriendLink> listFriendLink() {
        String friendLink = (String) redisTemplate.opsForValue().get("FriendLink");
        if (!StringUtils.isEmpty(friendLink)) {
            List<FriendLink> friendLinks = JSON.parseArray(friendLink, FriendLink.class);
            return friendLinks;
        } else {
            List<FriendLink> friendLinkList = friendLinkMapper.listFriendLink();
            String json = JSON.toJSONString(friendLinkList);
            redisTemplate.opsForValue().set("FriendLink", json, 1, TimeUnit.DAYS);
            return friendLinkList;
        }
    }

    @Override
    public int saveFriendLink(FriendLink friendLink) {
        friendLink.setCreateTime(new Date());
        return friendLinkMapper.saveFriendLink(friendLink);
    }

    @Override
    public FriendLink getFriendLinkByBlogaddress(String blogaddress) {
        return friendLinkMapper.getFriendLinkByBlogaddress(blogaddress);
    }

    @Override
    public int updateFriendLink(FriendLink friendLink) {
        return friendLinkMapper.updateFriendLink(friendLink);
    }

    @Override
    public FriendLink getFriendLink(Long id) {
        return friendLinkMapper.getFriendLink(id);
    }

    @Override
    public void deleteFriendLink(Long id) {
        friendLinkMapper.deleteFriendLink(id);
    }
}
