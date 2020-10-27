package com.lloyvet.gulimall.coupon.dao;

import com.lloyvet.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 10:34:50
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
