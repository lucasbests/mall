package com.southwind.mmall002.service;

import com.southwind.mmall002.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
public interface ProductService extends IService<Product> {

    List<Product> findByTypeAndId(String type, Integer id);

    List<Product> searchFromEs(String keyword);

}
