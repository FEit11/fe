package com.feidea.controller.admin;

import com.feidea.entity.FriendLink;
import com.feidea.service.FriendLinkService;
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
 * 友链控制器
 */
@Controller
@RequestMapping("/admin")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;

    //查询所有友链
    @GetMapping("/friendlinks")
    public String friendlinks(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 10);
        List<FriendLink> friendLinks = friendLinkService.listFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<>(friendLinks);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/friendlinks";
    }

    //跳转到新增页面
    @GetMapping("/friendlinks/input")
    public String input(Model model) {
        model.addAttribute("friendlink", new FriendLink());
        return "admin/friendlinks-input";
    }

    //实现新增功能
    @PostMapping("/friendlinks")
    public String post(FriendLink friendLink, BindingResult result, RedirectAttributes redirectAttributes) {
        //检查是否已存在该网址
        FriendLink friendLinkByBlogaddress = friendLinkService.getFriendLinkByBlogaddress(friendLink.getBlogaddress());
        if (friendLinkByBlogaddress != null) {
            redirectAttributes.addFlashAttribute("message", "不能添加相同的网址");
            return "redirect:/admin/friendlinks/input";
        }

        if(result.hasErrors()){
            return "admin/friendlinks-input";
        }

        int flag = friendLinkService.saveFriendLink(friendLink);
        if (flag == 0 ) {
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/friendlinks";
    }

    //跳转到编辑页面
    @GetMapping("/friendlinks/{id}/input")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("friendlink", friendLinkService.getFriendLink(id));
        return "admin/friendlinks-input";
    }

    //实现友链编辑功能
    @PostMapping("/friendlinks/{id}")
    public String editPost(FriendLink friendLink, RedirectAttributes redirectAttributes) {
        int flag = friendLinkService.updateFriendLink(friendLink);
        if (flag == 0) {
            redirectAttributes.addFlashAttribute("message", "编辑失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/friendlinks";
    }

    //删除友链实现
    @GetMapping("/friendlinks/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        friendLinkService.deleteFriendLink(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/friendlinks";
    }
}
