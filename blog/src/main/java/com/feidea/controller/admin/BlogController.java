package com.feidea.controller.admin;

import com.feidea.entity.Blog;
import com.feidea.entity.Type;
import com.feidea.entity.User;
import com.feidea.service.BlogService;
import com.feidea.service.TypeService;
import com.feidea.vo.BlogQuery;
import com.feidea.vo.ShowBlog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    //博客列表
    @RequestMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,10,orderBy);
        List<BlogQuery> list = blogService.getAllBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(list);
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

    //跳转博客新增页面
    @GetMapping("/blogs/input")
    public String input(Model model) {
        //传输所有可选类型
        model.addAttribute("types",typeService.getAllType());
        //传输一个空的博客信息
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    //博客新增
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //获取用户信息
        User user = (User) session.getAttribute("user");
        //设置新增该博客的用户
        blog.setUser(user);
        //设置blog的type
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        int flag = blogService.saveBlog(blog);
        if(flag == 0){
            attributes.addFlashAttribute("message", "新增失败");
        }else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/blogs";
    }

    //跳转博客编辑页面
    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> allType = typeService.getAllType();
        model.addAttribute("blog", blogById);
        model.addAttribute("type", allType);
        return "admin/blogs-input";
    }

    //实现博客编辑功能
    @PostMapping("/blogs/{id}")
    public String editPost(ShowBlog showBlog, RedirectAttributes redirectAttributes) {
        int flag = blogService.updateBlog(showBlog);
        if(flag ==0){
            redirectAttributes.addFlashAttribute("message", "修改失败");
        }else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/blogs";
    }


    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
