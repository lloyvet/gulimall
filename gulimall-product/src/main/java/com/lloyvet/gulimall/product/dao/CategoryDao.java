package com.lloyvet.gulimall.product.dao;

import com.lloyvet.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-07 21:54:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
