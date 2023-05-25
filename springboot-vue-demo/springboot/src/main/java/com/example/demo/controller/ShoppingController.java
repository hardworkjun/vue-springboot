package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Shop;
import com.example.demo.entity.Shopping;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.mapper.ShoppingMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.Date;

//定义返回值为json的controller
@RestController
//定义路由
@RequestMapping("/shopping")
public class ShoppingController {
    @Resource
    ShoppingMapper Shoppingmapper;

    //    新增
    @PostMapping
    public Result<?> save(@RequestBody Shopping Shopping) {
//        Shopping.setOrder1(String.valueOf(System.currentTimeMillis()));
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        Shopping.setData2(formatter.toString());
        Shoppingmapper.insert(Shopping);
        return Result.success();
    }

    //    更新
    @PutMapping
    public Result<?> update(@RequestBody Shopping shopping,
                            @RequestParam(defaultValue = "") String choice) {
        if (choice.equals("1"))//修改发货状态
        {
            LambdaUpdateWrapper<Shopping> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Shopping::getOrder1, shopping.getOrder1()).set(Shopping::getPusher, "1");
            Integer rows = Shoppingmapper.update(null, lambdaUpdateWrapper);
        }
        else if(choice.equals("2"))//修改收货状态
        {
            LambdaUpdateWrapper<Shopping> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(Shopping::getOrder1, shopping.getOrder1()).set(Shopping::getGeter, "1");
            Integer rows = Shoppingmapper.update(null, lambdaUpdateWrapper);
        }
        else if(choice.equals("3"))
        {

        }
        return Result.success();

    }

    //    删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Shoppingmapper.deleteById(id);
        return Result.success();
    }

    //    查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10000") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,//客户查询订单
                              @RequestParam(defaultValue = "") String search2,//客服查询订单
                              @RequestParam(defaultValue = "") String search,//客服查询订单
                              @RequestParam(defaultValue = "") String choice,
                              @RequestParam(defaultValue = "") String order1
    ) {
        if (choice.equals("1")) { //1.订单页面详情查询
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            wrapper.eq(Shopping::getOrder1, order1);
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if (choice.equals("2")) { //2.1订单页面初始加载
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
                wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "0").groupBy(Shopping::getOrder1);
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if(choice.equals("3")) {//2.2订单页面搜索
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
                if (search.equals("")){//2.2.1搜索为空处理
                        wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "0").groupBy(Shopping::getOrder1);

                }
                else {//2.2.2搜索不为空处理
                    wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "0").like(Shopping::getOrder1,search).groupBy(Shopping::getOrder1);
                  }
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if(choice.equals("4")){//3.1已收货页面加载
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "1").groupBy(Shopping::getOrder1);
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if(choice.equals("5")){//3.2已收货页面搜索
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            if (search.equals("")){//3.2.1搜索为空处理
                wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "1").groupBy(Shopping::getOrder1);

            }
            else {//3.2.2搜索不为空处理
                wrapper.eq(Shopping::getNickName, search1).eq(Shopping::getGeter, "1").like(Shopping::getOrder1,search).groupBy(Shopping::getOrder1);
            }
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if(choice.equals("6")){//4.1.1客服页面订单管理加载
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            wrapper.eq(Shopping::getPusher, "0").ne(Shopping::getOrder1, "0").groupBy(Shopping::getOrder1);
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else if(choice.equals("7")) {//4.1.2客服页面订单管理搜索
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            if (search.equals("")){//2.2.1搜索为空处理
                wrapper.eq(Shopping::getPusher, "0").groupBy(Shopping::getOrder1);

            }
            else {//2.2.2搜索不为空处理
                wrapper.eq(Shopping::getPusher, "0").like(Shopping::getOrder1,search).groupBy(Shopping::getOrder1);
            }
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
            return Result.success(ShoppingPage);
        }
        else if(choice.equals("8")){//4.2.1客服页面已发货订单管理加载
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            wrapper.eq(Shopping::getPusher, "1").groupBy(Shopping::getOrder1);
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }
        else {//4.2.2客服页面已收货订单管理搜索
            LambdaQueryWrapper<Shopping> wrapper = Wrappers.<Shopping>lambdaQuery();
            if (search.equals("")){//3.2.1搜索为空处理
                wrapper.eq(Shopping::getPusher, "1").groupBy(Shopping::getOrder1);

            }
            else {//3.2.2搜索不为空处理
                wrapper.eq(Shopping::getPusher, "1").like(Shopping::getOrder1,search).groupBy(Shopping::getOrder1);
            }
            Page<Shopping> ShoppingPage = Shoppingmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

            return Result.success(ShoppingPage);
        }


    }

}
