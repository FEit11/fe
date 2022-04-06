package com.feidea.service.impl;

import com.alibaba.fastjson.JSON;
import com.feidea.dao.PictureMapper;
import com.feidea.entity.Picture;
import com.feidea.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Picture> listPicture() {
        String picture = (String) redisTemplate.opsForValue().get("Picture");
        if (!StringUtils.isEmpty(picture)) {
            List<Picture> pictures = JSON.parseArray(picture, Picture.class);
            return pictures;
        } else {
            List<Picture> pictureList = pictureMapper.listPicture();
            String json = JSON.toJSONString(pictureList);
            redisTemplate.opsForValue().set("Picture", json, 1, TimeUnit.DAYS);
            return pictureList;
        }
    }

    @Override
    public int savePicture(Picture picture) {
        return pictureMapper.savePicture(picture);
    }

    @Override
    public int updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }

    @Override
    public Picture getPicture(Long id) {
        return pictureMapper.getPicture(id);
    }

    @Override
    public void deletePicture(Long id) {
        pictureMapper.deletePicture(id);
    }
}
