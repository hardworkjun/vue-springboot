package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Enterprise;
import com.example.demo.entity.Enterprise;
import com.example.demo.mapper.EnterpriseMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
//定义返回值为json的controller
@RestController
//定义路由
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Resource
    EnterpriseMapper Enterprisemapper;
    //    新增
    @PostMapping
    public Result <?> save(@RequestBody Enterprise Enterprise){

        Enterprisemapper.insert(Enterprise);
        return Result.success();
    }
    //    更新
    @PutMapping
    public Result <?> update(@RequestBody Enterprise Enterprise){
        Enterprisemapper.updateById(Enterprise  );
        return Result.success();
    }
    //    删除
    @DeleteMapping("/{id}")
    public Result <?> delete(@PathVariable Long id){
        Enterprisemapper.deleteById(id);
        return Result.success();
    }
    //    查询
    @GetMapping
    public Result <?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Enterprise> wrapper= Wrappers.<Enterprise>lambdaQuery();
        // 避免搜索项是空的时候 无法查询结果
        if (StrUtil.isNotBlank(search)){
            wrapper.eq(Enterprise::getUsername,search);
        }
        Page<Enterprise> EnterprisePage= Enterprisemapper.selectPage(new Page<>(pageNum,pageSize),
                wrapper);

        return Result.success(EnterprisePage);
    }
}
