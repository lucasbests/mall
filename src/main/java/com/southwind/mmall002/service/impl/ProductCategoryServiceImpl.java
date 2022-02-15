package com.southwind.mmall002.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.mmall002.entity.Product;
import com.southwind.mmall002.entity.ProductCategory;
import com.southwind.mmall002.mapper.ProductCategoryMapper;
import com.southwind.mmall002.mapper.ProductMapper;
import com.southwind.mmall002.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.mmall002.vo.ProductCategoryVO;
import com.southwind.mmall002.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper mapper;
    @Autowired
    private ProductMapper productMapper;
    public List<ProductCategoryVO> getAllList(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",1);
        List<ProductCategory> levelOne = mapper.selectList(wrapper);
        List<ProductCategoryVO> levelOneVo = levelOne.stream()
                .map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
        for (int i = 0; i < levelOneVo.size(); i++) {
            levelOneVo.get(i).setBannerImg("/images/banner"+i+".png");
            levelOneVo.get(i).setTopImg("/images/top"+i+".png");
            wrapper = new QueryWrapper();
            wrapper.eq("categorylevelone_id",levelOneVo.get(i).getId());
            List<Product> productList = productMapper.selectList(wrapper);
            List<ProductVO> productVOList = productList.stream()
                    .map(e -> new ProductVO(
                            e.getId(),
                            e.getName(),
                            e.getPrice(),
                            e.getFileName()
                    )).collect(Collectors.toList());
            levelOneVo.get(i).setProductVOList(productVOList);
        }
        for (ProductCategoryVO productCategoryVO : levelOneVo) {
            wrapper = new QueryWrapper();
            wrapper.eq("type",2);
            wrapper.eq("parent_id",productCategoryVO.getId());
            List<ProductCategory> levelTwo =  mapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVo = levelTwo.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
            productCategoryVO.setChildren(levelTwoVo);
            for (ProductCategoryVO categoryVO : levelTwoVo) {
                wrapper = new QueryWrapper();
                wrapper.eq("type",3);
                wrapper.eq("parent_id",categoryVO.getId());
                List<ProductCategory> levelThree =  mapper.selectList(wrapper);
                List<ProductCategoryVO> levelThreeVo = levelThree.stream().map(e -> new ProductCategoryVO(e.getId(), e.getName())).collect(Collectors.toList());
                categoryVO.setChildren(levelThreeVo);
            }
        }
        return levelOneVo;
    }

}
