package com.swjd.controller;

import com.swjd.bean.User;
import com.swjd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    // 去登录
    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }

    // 判断登录
    @RequestMapping("/doLogin")
    public String doLogin(Model model, User user, HttpSession session){//model为了传递错误信息
        User u=userService.login(user);
        if (u!=null) {
            // 账号密码没错
            if (u.getFlag().equals("1")) {
                // 1正常
                //登录成功吧用户名存到session
                session.setAttribute("activeName",u.getuName());

                return "redirect:/customerController/toMain";
            } else {
                // 0禁用
                model.addAttribute("errorMsg","该账号被禁用，请联系管理员。");
                model.addAttribute("user",user);
                return "login";
            }
        } else {
            // 账号密码错误
            model.addAttribute("errorMsg","账号或者密码错误！");
            model.addAttribute("user",user);
            return "login";
        }
    }

    //去main.jsp
    @RequestMapping("/toMain")
    public String toMain(){

        return "main";
    }

    //去car.jsp
    @RequestMapping("/toCar")
    public String toCar(){

        return "car";
    }

    //去my.jsp
    @RequestMapping("/toMy")
    public String toMy(){

        return "my";
    }

    //退出账号
    @RequestMapping("/logout")
    public String logout(HttpSession session,Model model){
        //让session过期的方法
        session.invalidate();

        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }
}
