package com.southwind.mmall002.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Lucas *
 * @Date: 2021/1/23 12:11 *
 */
@Data
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;
    private String bannerImg;
    private String topImg;
    private List<ProductVO> productVOList;
    public ProductCategoryVO(Integer id, String name){
        this.name = name;
        this.id = id;
    }
}
