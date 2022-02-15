package com.southwind.mmall002.controller;

import com.southwind.mmall002.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Lucas *
 * @Date: 2021/1/25 13:47 *
 */
@SpringBootTest
class CartControllerTest {
    @Autowired
    private CartService cartService;
    @Test
    void find(){
        cartService.findAllCartVOByUserId(10).forEach(System.out::println);
    }


}