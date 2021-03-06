package com.lloyvet.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.gulimall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu信息介绍
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-07 21:54:35
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity descEntity);
}

