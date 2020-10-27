package com.lloyvet.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lloyvet.gulimall.product.dao.CategoryBrandRelationDao;
import com.lloyvet.gulimall.product.service.CategoryBrandRelationService;
import com.lloyvet.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lloyvet.common.utils.PageUtils;
import com.lloyvet.common.utils.Query;

import com.lloyvet.gulimall.product.dao.CategoryDao;
import com.lloyvet.gulimall.product.entity.CategoryEntity;
import com.lloyvet.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> entities = categoryDao.selectList(null);
        //组装成父子树形结构
        //找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
            categoryEntity.getParentCid() == 0
        ).map((menu) ->{
            menu.setChildren(getChildren(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        categoryDao.deleteBatchIds(asList);
    }

    /**
     * 找到catelogId的完整路径
     * 父/子/孙路径
     * @param catelogId
     * @return
     */
    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findParentPath(catelogId,paths);
        Collections.reverse(paths);
        return (Long[])paths.toArray(new Long[paths.size()]);
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @CacheEvict(value = "category",key = "'level1Categorys'")
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        categoryDao.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        //同时修改缓存中的数据
        //删除缓存中的数据，等待下次数据更新
        redisTemplate.delete("catalogJson");

    }

    @Cacheable(value = {"category"},key = "'level1Categorys'")
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid",0));
    }

    //从数据库中查询并封装数据
    public Map<String, List<Catelog2Vo>>  getCatalogJsonFromDbWithLocalLock() {
       synchronized (this){
           return getDataFromDb();
       }
    }

    public Map<String, List<Catelog2Vo>>  getCatalogJsonFromDbWithRedisLock() {
        //锁的粒度，越细越好
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        //抢占分布式锁
        //String uuid = UUID.randomUUID().toString();
        //Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid,30,TimeUnit.SECONDS);
            //加锁成功,执行业务
            //设置过期时间
        Map<String, List<Catelog2Vo>> dataFromDb;
        try {
            dataFromDb = getDataFromDb();
        } finally {
            lock.unlock();
            //使用lua脚本删除锁
            //String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            //redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
        }
        return dataFromDb;
//        }else{
//            //加锁失败，重试
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return getCatalogJsonFromDbWithRedisLock();
//
//        }

    }
    public Map<String, List<Catelog2Vo>> getDataFromDb(){
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
            return catalogJsonFromDb;
        }
        /**
         * 优化查询，将多次查询变为1此
         */
        List<CategoryEntity> categoryEntitys = baseMapper.selectList(null);
        //查出所有1级分类
        List<CategoryEntity> level1Categorys = this.getCategoryByParent_cid(categoryEntitys, 0L);
        //封装数据
        Map<String, List<Catelog2Vo>> collect = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //每一个的一级分类，查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getCategoryByParent_cid(categoryEntitys, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null && categoryEntities.size() > 0) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    //找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> entities = getCategoryByParent_cid(categoryEntitys, l2.getCatId());
                    List<Catelog2Vo.Catelog3Vo> catelog3Vos = null;
                    if (entities != null) {
                        catelog3Vos = entities.stream().map(l3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                    }
                    catelog2Vo.setCatalog3List(catelog3Vos);
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        redisTemplate.opsForValue().set("catalogJson", JSON.toJSONString(collect), 1, TimeUnit.DAYS);
        return collect;
    }
    //TODO 产生堆外内存溢出
    //springboot2.0以后默认使用lettuce作为操作redis的客户端，使用netty进行网络通信
    //lettuce的bug导致netty堆外内存溢出
    @Override
    public Map<String, List<Catelog2Vo>>  getCatalogJson() {
        //加入缓存逻辑
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        Map<String, List<Catelog2Vo>> catalogJsonFromDb = null;
        if(StringUtils.isEmpty(catalogJson)){
            //缓存中没有查询数据库
            catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();
            redisTemplate.opsForValue().set("catalogJson",JSON.toJSONString(catalogJsonFromDb));
        }
        catalogJsonFromDb = JSON.parseObject(catalogJson,new TypeReference<Map<String, List<Catelog2Vo>>>(){});
        return catalogJsonFromDb;
    }

    private List<CategoryEntity> getCategoryByParent_cid(List<CategoryEntity> categoryEntitys,Long parent_cid) {
        List<CategoryEntity> collect = categoryEntitys.stream().filter(item -> {
            return item.getParentCid() == parent_cid;
        }).collect(Collectors.toList());
        return collect;
    }


    public void findParentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(categoryEntity.getParentCid()!=0){
            findParentPath(categoryEntity.getParentCid(),paths);
        }
    }
    //递归查找所有菜单子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter((categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        })).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}