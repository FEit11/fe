package com.feidea.dao;

import com.feidea.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper {

    //查询所有照片
    List<Picture> listPicture();

    //新增照片
    int savePicture(Picture picture);

    //修改照片信息
    int updatePicture(Picture picture);

    //通过id获取照片信息
    Picture getPicture(Long id);

    //删除照片
    void deletePicture(Long id);
}
