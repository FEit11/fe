package com.feidea.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.feidea.entity.Message;
import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    //查询父级评论
    List<Message> findByParentIdNull(@Param("ParentId") Long ParentId);

    //查询一级回复
    List<Message> findByParentIdNotNull(@Param("id") Long id);

    //查询二级以及所有子集回复
    List<Message> findByReplayId(@Param("childId") Long childId);

    //新增留言
    int saveMessage(Message message);

    //删除留言
    void deleteMessage(Long id);
}
