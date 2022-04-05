package com.tom.controller;

import com.tom.pojo.Admin;
import com.tom.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author tom
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpSession session,Model model){

        Admin admin = adminService.login(name,pwd);
        if (admin != null) {
            session.setAttribute("admin",admin);
            return "redirect:main.jsp";
        }else {
            model.addAttribute("errmsg","用户名或密码不正确");
            return "login";
        }

    }

}
