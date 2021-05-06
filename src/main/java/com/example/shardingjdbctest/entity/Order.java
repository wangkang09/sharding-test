package com.example.shardingjdbctest.entity;


import lombok.Data;
import java.util.Date;

@Data
public class Order {
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
