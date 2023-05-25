package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
//定义返回值为json的controller
@RestController
//定义路由
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper usermapper;
//    新增
    @PostMapping
    public Result <?> save(@RequestBody User user){
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        usermapper.insert(user);
        return Result.success();
    }
//    更新
    @PutMapping
    public Result <?> update(@RequestBody User user){
        usermapper.updateById(user  );
        return Result.success();
    }
//    删除
    @DeleteMapping("/{id}")
    public Result <?> delete(@PathVariable Long id){
        usermapper.deleteById(id);
        return Result.success();
    }
//    查询
    @GetMapping
    public Result <?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "") String role){
       LambdaQueryWrapper<User> wrapper= Wrappers.<User>lambdaQuery();
       if(role.equals("role")){
           wrapper.eq(User::getUsername,search);
           Page<User> userPage= usermapper.selectPage(new Page<>(pageNum,pageSize),
                   wrapper);
           return Result.success(userPage);
       }else{


       // 避免搜索项是空的时候 无法查询结果
       if (StrUtil.isNotBlank(search)){
           wrapper.like(User::getUsername,search);
       }
       Page<User> userPage= usermapper.selectPage(new Page<>(pageNum,pageSize),
               wrapper);

        return Result.success(userPage);
       }
    }
//    登录
    @PostMapping("/login")
    public Result <?> login(@RequestBody User user){
       User res= usermapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
       if(res==null)
       {
           return Result.error("-1","用户名或密码错误");
       }
        return Result.success();
    }
//     注册
    @PostMapping("/register")
    public Result <?> register(@RequestBody User user){
        User res= usermapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(res!=null)
        {
            return Result.error("-1","用户名重复");
        }
        if(user.getPassword()==null)
        {
            user.setPassword("123456");
        }
        user.setRole("2");
        usermapper.insert(user);
        return Result.success();
    }
}
