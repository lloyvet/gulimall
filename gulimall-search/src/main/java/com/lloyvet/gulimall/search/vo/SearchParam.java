package com.lloyvet.gulimall.search.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {

    private String keyword; //页面传来的全文匹配关键字
    private Long catalog3Id; //三级分类id
    private String sort;    //排序条件
    private  Integer hasStock; //是否只显示有货
    private String skuPrice; //价格区间查询
    private List<Long> brandId; //品牌id
    private List<String> attrs; //按照属性进行筛选
    private Integer pageNum; //页码
}
