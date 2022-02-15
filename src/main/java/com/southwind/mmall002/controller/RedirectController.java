package com.southwind.mmall002.controller;

import com.southwind.mmall002.entity.User;
import com.southwind.mmall002.service.CartService;
import com.southwind.mmall002.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * @Author: Lucas *
 * @Date: 2021/1/22 19:46 *
 */
@Controller
public class RedirectController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;
    @GetMapping("/{url}")
    public ModelAndView redirect(@PathVariable("url") String url, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list",productCategoryService.getAllList());
        User user = (User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        modelAndView.setViewName(url);
        return modelAndView;
    }
    @GetMapping("/")
    public String toMain(){
        return "redirect:/productCategory/list";
    }
}
