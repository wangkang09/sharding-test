package com.example.shardingjdbctest.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shardingjdbctest.entity.Order;
import com.example.shardingjdbctest.mapper.OrderMapper;
import com.example.shardingjdbctest.service.OrderService;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderService orderService;

    // https://github.com/apache/shardingsphere/issues/10105
    // rewrite sql error when using keyGenerator and encrypt together
    @PostMapping("/insert_email")
    public Order insert(@RequestParam(name = "email", required = false) String email) {
        orderMapper.delete(new LambdaQueryWrapper<Order>().ne(Order::getMemberId, 9898L));

        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        if (email != null) {
            order.setEmail(email);
        }
        orderMapper.insert(order);
        return order;
    }

    @PostMapping("/insert_batch")
    public Order insertBatch() {
        orderMapper.delete(new LambdaQueryWrapper<Order>().ne(Order::getMemberId, 9898L));

        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        List<Order> list = new ArrayList<>();
        list.add(order);
        order = new Order();
        order.setMemberId(2L);
        order.setBillno("1");
        list.add(order);
        order = new Order();
        order.setMemberId(6L);
        order.setBillno("1");
        list.add(order);
        orderService.saveBatch(list);
        return order;
    }

    @PostMapping("/update_batch")
    public Order updateBatch() {

        orderMapper.delete(new LambdaQueryWrapper<Order>().ne(Order::getMemberId, 9898L));

        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        List<Order> list = new ArrayList<>();
        list.add(order);
        order = new Order();
        order.setMemberId(2L);
        order.setBillno("1");
        list.add(order);

        orderService.saveBatch(list);
        return order;
    }

    @GetMapping("/getOrder")
    public List<Order> select(@RequestParam("memberId") Long memberId, @RequestParam(name = "master", required = false) Boolean isMaster) {
        if (Boolean.TRUE.equals(isMaster)) {
            HintManager.getInstance().setPrimaryRouteOnly();
        }
        try {
            return orderMapper.selectList(new LambdaQueryWrapper<Order>().in(Order::getMemberId, Arrays.asList(1, 0, 2)));
        } finally {
            HintManager.clear();
        }
    }

    @PostMapping("/insert_batch_email")
    public Order insertBatchEmail() {
        orderMapper.delete(new LambdaQueryWrapper<Order>().ne(Order::getMemberId, 9898L));

        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        order.setEmail("dd");
        List<Order> list = new ArrayList<>();
        list.add(order);
        order = new Order();
        order.setMemberId(2L);
        order.setBillno("1");
        list.add(order);
        order = new Order();
        order.setMemberId(6L);
        order.setBillno("1");
        order.setEmail("dd");
        list.add(order);
        orderService.saveBatch(list);
        return order;
    }
}
