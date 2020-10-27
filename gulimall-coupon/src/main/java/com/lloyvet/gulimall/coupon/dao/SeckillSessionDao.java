package com.lloyvet.gulimall.coupon.dao;

import com.lloyvet.gulimall.coupon.entity.SeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 10:34:50
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {
	
}
