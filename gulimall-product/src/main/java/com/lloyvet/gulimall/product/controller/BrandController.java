package com.lloyvet.gulimall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.lloyvet.common.valid.AddGroup;
import com.lloyvet.common.valid.UpdateGroup;
import com.lloyvet.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lloyvet.gulimall.product.entity.BrandEntity;
import com.lloyvet.gulimall.product.service.BrandService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-07 21:54:36
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateDetail(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated(value = {UpdateStatusGroup.class}) @RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
