package com.feidea.controller;

import com.feidea.entity.Comment;
import com.feidea.service.BlogService;
import com.feidea.service.CommentService;
import com.feidea.vo.DetailedBlog;
import com.feidea.vo.FirstPageBlog;
import com.feidea.vo.RecommendBlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<FirstPageBlog> firstPageBlogs = blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendBlogs = blogService.getRecommendedBlog();
        PageInfo<FirstPageBlog> firstPageBlogPageInfo = new PageInfo<>(firstPageBlogs);
        model.addAttribute("pageInfo", firstPageBlogPageInfo);
        model.addAttribute("recommendedBlogs", recommendBlogs);
        return "index";
    }

    //通过输入的参数查询博客
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query, Model model) {
        PageHelper.startPage(pageNum, 1000);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    //跳转到博客信息
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("blog", detailedBlog);
        model.addAttribute("comments", comments);
        return "blog";
    }

    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();
        int blogViewTotal = blogService.getBlogViewTotal();
        int blogCommentTotal = blogService.getBlogCommentTotal();
        int blogMessageTotal = blogService.getBlogMessageTotal();

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);
        return "index :: blogMessage";
    }
}