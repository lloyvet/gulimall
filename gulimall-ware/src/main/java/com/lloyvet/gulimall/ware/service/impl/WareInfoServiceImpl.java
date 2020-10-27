package com.lloyvet.gulimall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.common.utils.Query;

import com.lloyvet.gulimall.ware.dao.WareInfoDao;
import com.lloyvet.gulimall.ware.entity.WareInfoEntity;
import com.lloyvet.gulimall.ware.service.WareInfoService;
import org.springframework.util.StringUtils;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>().eq(!StringUtils.isEmpty(key),"id",key)
                .or().like(!StringUtils.isEmpty(key),"name",key)
                .or().like(!StringUtils.isEmpty(key),"address",key)
                .or().like(!StringUtils.isEmpty(key),"areacode",key)
        );

        return new PageUtils(page);
    }

}