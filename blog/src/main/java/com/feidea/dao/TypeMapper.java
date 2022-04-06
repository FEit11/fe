package com.feidea.dao;

import com.feidea.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TypeMapper {

    //新增类型
    int saveType(Type type);

    //通过id获取对应博客类型
    Type getType(Long id);

    //获取所有博客类型
    List<Type> getAllType();

    //通过类型名获取类型
    Type getTypeByName(String name);

    //更新博客类型
    int updateType(Type type);

    //删除博客类型
    int deleteType(Long id);

    //获取所有分类及对应博客
    List<Type> getAllTypeAndBlog();
}
