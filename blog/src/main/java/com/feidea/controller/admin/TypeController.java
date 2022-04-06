package com.feidea.controller.admin;

import com.feidea.entity.Type;
import com.feidea.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 分类管理控制器
 */
@Controller
@RequestMapping("/admin")
public class TypeController {


    @Autowired
    private TypeService typeService;

    //分页显示博客分类
    @GetMapping("/types")
    public String types(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum) {
        //按照降序排序
        String orderBy = "id desc";
        //开启分页
        PageHelper.startPage(pageNum, 10, orderBy);
        //调用方法获取全部分类信息
        List<Type> allType = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<Type>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    //类型新增页面
    @GetMapping("/types/input")
    public String typesInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //类型新增
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int flag = typeService.saveType(type);
        if (flag == 0) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    //PathVariable是url地址栏中的占位符
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //编辑分类实现
    @PostMapping("/types/{id}")
    public String editPost(Type type, RedirectAttributes redirectAttributes) {
        Type typeByName = typeService.getTypeByName(type.getName());
        if (typeByName != null) {
            redirectAttributes.addFlashAttribute("message", "不能添加重复的分类");
            return "redirect:/admin/types/input";
        }
        int flag = typeService.updateType(type);
        if (flag > 0) {
            redirectAttributes.addFlashAttribute("message", "编辑成功");
        } else {
            redirectAttributes.addFlashAttribute("message", "编辑失败");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @RequestMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
