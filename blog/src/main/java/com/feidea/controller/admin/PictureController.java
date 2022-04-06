package com.feidea.controller.admin;


import com.feidea.entity.Picture;
import com.feidea.service.PictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 照片墙控制器
 */
@Controller
@RequestMapping("/admin")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //显示照片管理页面
    @GetMapping("/pictures")
    public String pictures(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<Picture> pictures = pictureService.listPicture();
        PageInfo<Picture> pageInfo = new PageInfo<>(pictures);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/pictures";
    }

    //跳转新增页面
    @GetMapping("/pictures/input")
    public String input(Model model) {
        model.addAttribute("picture", new Picture());
        return "admin/pictures-input";
    }

    //实现新增照片功能
    @PostMapping("/pictures")
    public String picturesPost(Picture picture, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()){
            return "admin/pictures-input";
        }

        int flag = pictureService.savePicture(picture);
        if (flag == 0 ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/pictures";
    }

    //跳转到编辑页面
    @GetMapping("/pictures/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("picture", pictureService.getPicture(id));
        return "admin/pictures-input";
    }

    //实现照片信息编辑
    @PostMapping("/pictures/{id}")
    public String editPost(Picture picture, RedirectAttributes redirectAttributes) {
        int flag = pictureService.updatePicture(picture);
        if(flag == 0) {
            redirectAttributes.addFlashAttribute("message", "编辑失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/pictures";
    }

    //实现照片删除
    @GetMapping("/pictures/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pictureService.deletePicture(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/pictures";
    }

}
