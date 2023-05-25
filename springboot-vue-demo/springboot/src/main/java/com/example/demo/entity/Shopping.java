package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

//对应数据库表名
@TableName("shopping")
//自动生成get set方法
@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shopping {
    //    定义id自增
    @TableId(type = IdType.AUTO)
//    对应数据库属性名
    private Integer id;
    private String order1;
    private String nickName;
    private String name;
    private Date date1;
    private String date2;
    private String address;
    private String say;
    private String pusher;
    private String date3;
    private String geter;
    private String date4;
    private String pid;




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