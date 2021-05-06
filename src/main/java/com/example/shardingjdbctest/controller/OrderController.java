package com.example.shardingjdbctest.controller;


import com.example.shardingjdbctest.entity.Order;
import com.example.shardingjdbctest.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderMapper orderMapper;


    // https://github.com/apache/shardingsphere/issues/10105
    // rewrite sql error when using keyGenerator and encrypt together
    @PostMapping("/insert_email")
    public Order insert(@RequestParam(name = "email", required = false) String email) {
        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        if (email == null) {
            email = "22";
        }
        orderMapper.insert(1L, "1", email);
        return order;
    }

}
