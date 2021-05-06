package com.example.shardingjdbctest.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shardingjdbctest.entity.Order;
import com.example.shardingjdbctest.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * @author wangkang
 * @date 2021-04-15
 * @since -
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
