package com.lloyvet.gulimall.product.feign;

import com.lloyvet.common.utils.R;
import com.lloyvet.gulimall.product.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WareFeignService {

    @PostMapping("/ware/waresku/hasStock")
    public R getSkusHasStock(@RequestBody List<Long> skuIds);
}
