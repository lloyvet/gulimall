package com.lloyvet.gulimall.product.feign;

import com.lloyvet.common.to.SkuReductionTo;
import com.lloyvet.common.to.SpuBoundTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * ,@RequestBody将这个对象转为JSOn
     * 找到gulimall-coupon服务，给/coupon/spubounds/save发送请求，将上一步赚的json放在请求体位置，发送请求
     * @param spuBoundTo
     */
    @PostMapping("/coupon/spubounds/save")
    void saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    void saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
