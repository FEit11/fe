package com.feidea.controller;

import com.feidea.entity.Blog;
import com.feidea.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 时间轴控制器
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        List<Blog> allBlog = blogService.getAllBlog();
        model.addAttribute("blogs", allBlog);
        return "archives";
    }
}
