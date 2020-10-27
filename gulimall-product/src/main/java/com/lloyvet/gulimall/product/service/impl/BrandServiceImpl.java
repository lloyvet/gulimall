package com.lloyvet.gulimall.product.service.impl;

import com.lloyvet.gulimall.product.dao.CategoryBrandRelationDao;
import com.lloyvet.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.common.utils.Query;

import com.lloyvet.gulimall.product.dao.BrandDao;
import com.lloyvet.gulimall.product.entity.BrandEntity;
import com.lloyvet.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String)params.get("key");
        QueryWrapper<BrandEntity> qw = new QueryWrapper<>();
        qw.eq(!StringUtils.isEmpty(key),"brand_id",key)
                .or().like(!StringUtils.isEmpty(key),"name",key);
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params),qw);

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余字段的数据一致
        brandDao.updateById(brand);
        if(!StringUtils.isEmpty(brand.getName())){
            //同步更新其他关联表中的数据
            categoryBrandRelationService.updateBrand(brand.getBrandId(),brand.getName());
            //TODO
        }

    }

}