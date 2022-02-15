package com.southwind.mmall002.service;

import com.southwind.mmall002.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.mmall002.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    List<ProductCategoryVO> getAllList();
}
