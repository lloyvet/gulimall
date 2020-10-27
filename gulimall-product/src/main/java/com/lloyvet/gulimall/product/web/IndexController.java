package com.lloyvet.gulimall.product.web;

import com.lloyvet.gulimall.product.entity.CategoryEntity;
import com.lloyvet.gulimall.product.service.CategoryService;
import com.lloyvet.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/","/index.html"})
    public String index(Model model){

        //1查出所有的一级分类
        List<CategoryEntity> categories = categoryService.getLevel1Categorys();
        model.addAttribute("categorys",categories);
        return "index";
    }

    @ResponseBody
    @GetMapping("index/json/catalog.json")
    public Map<String,List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>>  res = categoryService.getCatalogJson();
        return res;
    }
}
