package com.lloyvet.gulimall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lloyvet.gulimall.ware.vo.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lloyvet.gulimall.ware.entity.WareSkuEntity;
import com.lloyvet.gulimall.ware.service.WareSkuService;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.common.utils.R;



/**
 * 商品库存
 *
 * @author lloyvet
 * @email 1924143976@qq.com
 * @date 2020-10-08 11:22:06
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;
    /**
     * 查询sku是否有库存
     */
    @PostMapping("/hasStock")
    public R getSkusHasStock(@RequestBody List<Long> skuIds){
        //sku_id,stock
        List<SkuHasStockVo> vos = wareSkuService.getSkusHasStock(skuIds);
        return R.ok().put("data", vos);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
