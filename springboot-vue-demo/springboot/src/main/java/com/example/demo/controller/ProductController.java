package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Products;
import com.example.demo.entity.User;
import com.example.demo.mapper.ProductsMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.sql.Wrapper;
//定义返回值为json的controller
@RestController
//定义路由
@RequestMapping("/products")
public class ProductController {
    @Resource
    ProductsMapper Productsmapper;
    //    新增
    @PostMapping
    public Result <?> save(@RequestBody Products Products){

        Productsmapper.insert(Products);
        return Result.success();
    }
    //    更新
    @PutMapping
    public Result <?> update(@RequestBody Products Products){
        Productsmapper.updateById(Products  );
        return Result.success();
    }
    //    删除
    @DeleteMapping("/{id}")
    public Result <?> delete(@PathVariable Long id){
        Productsmapper.deleteById(id);
        return Result.success();
    }
    //    查询
    @GetMapping
    public Result <?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "100") Integer pageSize,
                               @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Products> wrapper= Wrappers.<Products>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Products::getFeatures,search);
        }
        Page<Products> ProductsPage= Productsmapper.selectPage(new Page<>(pageNum,pageSize), wrapper);

        return Result.success(ProductsPage);
    }


}
