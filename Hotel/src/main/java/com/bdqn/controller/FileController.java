package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.utils.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/file")
public class FileController {
    /**
     * 文件上传
     * @param attach
     * @return
     */
    @RequestMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile attach){
        //创建Map集合保存返回前端的JSON数据
        Map<String,Object> map = new HashMap<String, Object>();
        //判断是否有选中文件
        if(!attach.isEmpty()){
            //获取文件上传的地址
            String path = "/home/hotel/upload/";
            //获取源文件的名称
            String originalFilename = attach.getOriginalFilename();
            //获取文件后缀名
            String extension = FilenameUtils.getExtension(originalFilename);
            //重命名旧文件
            String newFileName = UUIDUtils.randomUUID() + "." +extension;
            //为了解决同一个文件夹下文件过多的问题,使用日期作为文件夹管理
            String datepath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //组装最终的文件名
            String finalFileName = datepath + "/" + newFileName;
            //创建文件对象
            File dest = new File(path,finalFileName);
            //判断该文件夹是否存在,不存在则创建文件夹
            if(!dest.getParentFile().exists()){
                //创建文件夹
                dest.getParentFile().mkdirs();
            }

            try {
                //进行文件上传
                attach.transferTo(dest);
                //设置layui所需要的数据格式
                map.put("code",0);
                map.put("msg","上传成功");
                Map<String,Object> datamap = new HashMap<String, Object>();
                datamap.put("src","/hotel/show/" + finalFileName);
                map.put("data",datamap);//文件数据
                map.put("imagePath", finalFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSON.toJSONString(map);
    }

}
