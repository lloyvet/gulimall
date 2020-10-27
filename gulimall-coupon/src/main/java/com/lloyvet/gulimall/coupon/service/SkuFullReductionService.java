package com.lloyvet.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lloyvet.common.to.SkuReductionTo;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 10:34:49
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

