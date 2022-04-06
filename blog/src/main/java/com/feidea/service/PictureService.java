package com.feidea.service;

import com.feidea.entity.Picture;

import java.util.List;

public interface PictureService {

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
