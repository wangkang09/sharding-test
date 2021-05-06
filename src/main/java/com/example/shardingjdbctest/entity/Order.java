package com.example.shardingjdbctest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("rs_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String billno;
    private String status;
    private Byte isDel;
    private Date addTime;
    private Date lastUpdateTime;
    private String email;
    private String emailPlain;
    private String emailHash;
}
