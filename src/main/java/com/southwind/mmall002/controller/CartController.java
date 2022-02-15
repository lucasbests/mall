package com.southwind.mmall002.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.mmall002.entity.Cart;
import com.southwind.mmall002.entity.User;
import com.southwind.mmall002.enums.ResultEnum;
import com.southwind.mmall002.exception.MallException;
import com.southwind.mmall002.service.CartService;
import com.southwind.mmall002.service.UserAddressService;
import com.southwind.mmall002.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Writer;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/add/{productId}/{price}/{quantity}")
    public String add(@PathVariable("productId") Integer productId,
                      @PathVariable("price") Float price,
                      @PathVariable("quantity") Integer quantity, HttpSession session){

        User user = (User) session.getAttribute("user");
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setCost(quantity*price);
        if (user==null){
            throw new MallException(ResultEnum.USER_NO_LOGIN);
        }
        cart.setUserId(user.getId());
        try {
            if(cartService.save(cart)){
                return "redirect:/cart/findAllCart";
            }
        } catch (Exception e) {
            return "redirect:/productCategory/list";
        }
        return null;
    }
    @GetMapping("/findAllCart")
    public ModelAndView findCart(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        modelAndView.setViewName("settlement1");
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        return modelAndView;
    }


    @GetMapping("settlement2")
    public ModelAndView toSettlement2(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        int i = 1;
        return modelAndView;
    }
    @GetMapping("/removeCart/{id}")
    public String remove(@PathVariable("id")Integer id){
        cartService.removeById(id);
        return "redirect:/cart/findAllCart";
    }


}

