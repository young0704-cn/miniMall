package com.home.miniMall.controller;

import com.home.miniMall.pojo.Admin;
import com.home.miniMall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login.action")
    public String method01(Admin admin, Model model, HttpServletRequest request){
        Admin user=adminService.login(admin);
        if (user==null){
            model.addAttribute("errmsg","用户名密码不正确");
            return "login";
        }
        request.getSession().setAttribute("admin",admin);
        return "main";
    }
}
