package com.feidea.service;

import com.feidea.entity.Message;

import java.util.List;

public interface MessageService {
    //查询留言列表
    List<Message> listMessage();

    //新增留言
    int saveMessage(Message message);

    //删除留言
    void deleteMessage(Long id);
}
