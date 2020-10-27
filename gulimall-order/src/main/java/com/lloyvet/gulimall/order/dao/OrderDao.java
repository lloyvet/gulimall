package com.lloyvet.gulimall.order.dao;

import com.lloyvet.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:13:06
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
