package com.lloyvet.gulimall.order.dao;

import com.lloyvet.gulimall.order.entity.OrderReturnReasonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退货原因
 * 
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:13:05
 */
@Mapper
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReasonEntity> {
	
}
