package com.southwind.mmall002.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.mmall002.entity.User;
import com.southwind.mmall002.enums.GenderEnum;
import com.southwind.mmall002.mapper.UserMapper;
import com.southwind.mmall002.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);
        User checkUser = userService.getOne(wrapper);
        if (checkUser==null){
            return "login";
        }else{
            session.setAttribute("user",checkUser);
            return "redirect:/productCategory/list";
        }

    }
    @PostMapping("/register")
    public String register(User user, Model model){
        boolean result = false;
        try {
            if(user.getGenderCode() == 1){
                user.setGender(GenderEnum.MAN);
            }
            if(user.getGenderCode() == 0){
                user.setGender(GenderEnum.WOMAN);
            }
            result = userService.save(user);
        } catch (Exception e) {
            model.addAttribute("error",user.getLoginName()+"已存在！请重新输入！");
            return "register";
        }
        if(result) return "login";
        return "register";
    }
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}

