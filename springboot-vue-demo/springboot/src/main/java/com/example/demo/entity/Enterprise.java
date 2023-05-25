package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//对应数据库表名
@TableName("enterprise")
//自动生成get set方法
@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {
    //    定义id自增
    @TableId(type = IdType.AUTO)
//    对应数据库属性名
    private Integer id;
    private String name;
    private String nickName;
    private String person;
    private String address;
    private String post;
    private String bank;
    private String account;
    private String username;





    //    private String avatar;
//    @TableField(exist = false)
//    private List<Integer> roles;
//
//    @TableField(exist = false)
//    private List<Book> bookList;
//
//    @TableField(exist = false)
//    private String token;
//
//    private BigDecimal account;
//
//    @TableField(exist = false)
//    private Set<Permission> permissions;
}