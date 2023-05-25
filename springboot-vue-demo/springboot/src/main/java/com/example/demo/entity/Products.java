package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//对应数据库表名
@TableName("products")
//自动生成get set方法
@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    //    定义id自增
    @TableId(type = IdType.AUTO)
//    对应数据库属性名
    private Integer id;
    private String name;
    private String price;
    private String features;
    private String image;

    
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