package com.lloyvet.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//二级分类
public class Catelog2Vo {

    private String catalog1Id; //1级分类id
    private List<Catelog3Vo> catalog3List; //三级子分类
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    //三级分类
    public static class Catelog3Vo{
        private String catalog2Id; //2级分类id
        private String id;
        private String name;
    }
}
