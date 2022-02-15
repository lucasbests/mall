package com.southwind.mmall002.mapper;

import com.southwind.mmall002.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 建强
 * @since 2021-01-21
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {


}
