package com.southwind.mmall002.controller;


import com.southwind.mmall002.entity.ProductCategory;
import com.southwind.mmall002.entity.User;
import com.southwind.mmall002.mapper.ProductCategoryMapper;
import com.southwind.mmall002.service.CartService;
import com.southwind.mmall002.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductCategoryMapper mapper;

    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("list")
    public ModelAndView getList(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",productCategoryService.getAllList());
        User user = (User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }
}

