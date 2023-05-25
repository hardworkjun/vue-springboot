package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Shop;
import com.example.demo.entity.Shop;
import com.example.demo.entity.Shopping;
import com.example.demo.mapper.ShopMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.sql.Wrapper;
//定义返回值为json的controller
@RestController
//定义路由
@RequestMapping("/shop")
public class ShopController {
    @Resource
    ShopMapper Shopmapper;
    //    新增
    @PostMapping
    public Result <?> save(@RequestBody Shop Shop){
        int count=Integer.parseInt(Shop.getPrice())*Integer.parseInt(Shop.getCount()) ;
        Shop.setNum(count);
        Shopmapper.insert(Shop);
        return Result.success();
    }
    //    更新
    @PutMapping
    public Result <?> update(@RequestBody Shop shop,
                             @RequestParam(defaultValue = "") String id,
                             @RequestParam(defaultValue = "") String choice
                             ){
        if (choice.equals("1"))//修改购买状态，已购买
        {
            LambdaUpdateWrapper<Shop> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Shop::getId,id).set(Shop::getBuy, "1");
            Integer rows = Shopmapper.update(null, lambdaUpdateWrapper);
        }
        else//修改购物车信息
        {
            int count=Integer.parseInt(shop.getPrice())*Integer.parseInt(shop.getCount()) ;
            shop.setNum(count);
            Shopmapper.updateById(shop);
        }

        return Result.success();
    }
    //    删除
    @DeleteMapping("/{id}")
    public Result <?> delete(@PathVariable Long id){
        Shopmapper.deleteById(id);
        return Result.success();
    }
    //    查询
    @GetMapping
    public Result <?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "100") Integer pageSize,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "") String choice,
                                @RequestParam(defaultValue = "") String pid
                              ){
        LambdaQueryWrapper<Shop> wrapper= Wrappers.<Shop>lambdaQuery();
        if (choice.equals("1"))
        {
            wrapper.eq(Shop::getId,pid).eq(Shop::getBuy,"1");
        }else{
            wrapper.eq(Shop::getNickName,search).eq(Shop::getBuy,"0");
        }
        Page<Shop> ShopPage= Shopmapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(ShopPage);
    }


}
