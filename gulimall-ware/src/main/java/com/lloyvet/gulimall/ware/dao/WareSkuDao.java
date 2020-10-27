package com.lloyvet.gulimall.ware.dao;

import com.lloyvet.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:22:06
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(Long skuId);
}
