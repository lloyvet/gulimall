package com.lloyvet.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.gulimall.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:22:06
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

