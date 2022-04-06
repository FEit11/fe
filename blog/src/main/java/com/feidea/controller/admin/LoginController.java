package com.feidea.controller.admin;

import com.feidea.entity.User;
import com.feidea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用户登录控制器
 */
@Controller
@RequestMapping(path = "/admin", method = {RequestMethod.POST,RequestMethod.GET})
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳到登录页面
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录效验
     * @param username
     * @param password
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password,
                        HttpSession httpSession, RedirectAttributes redirectAttributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            httpSession.setAttribute("user",user);
            return "admin/index";
        } else {
            redirectAttributes.addFlashAttribute("message", "用户名和密码错误");
        }
        return "redirect:/admin";
    }

    /**
     * 注销
     * @param httpSession
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/admin";
    }
}
