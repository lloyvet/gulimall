package com.lloyvet.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.gulimall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:13:05
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

