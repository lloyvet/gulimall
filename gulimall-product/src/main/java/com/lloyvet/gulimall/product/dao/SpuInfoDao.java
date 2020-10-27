package com.lloyvet.gulimall.product.dao;

import com.lloyvet.gulimall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-07 21:54:35
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updateSpuStatus(Long spuId, int code);
}
