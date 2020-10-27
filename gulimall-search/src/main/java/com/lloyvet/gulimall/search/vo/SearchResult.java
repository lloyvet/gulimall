package com.lloyvet.gulimall.search.vo;

import com.lloyvet.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {

    //查询到的所有商品信息
    private List<SkuEsModel> products;

    private Integer pageNum; //当前页面

    private Long total; //总记录数

    private Integer totalPages; //总页码

    private List<BrandVo> brands; //当前查询到的所有相关品牌

    private List<CatalogVo> catalogs; //当前查询到的结果所涉及到的所有分类

    private List<AttrVo> attrs; //当前查询到的所有涉及到的属性
    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;
    }















}
