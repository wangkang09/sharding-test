package com.example.shardingjdbctest.controller;


import cn.dotfashion.soa.api.vo.Response;
import cn.dotfashion.soa.tc.client.GlobalTxSpringContextHolder;
import cn.dotfashion.soa.tc.client.annotation.GlobalTxStart;
import com.example.shardingjdbctest.entity.Order;
import com.example.shardingjdbctest.mapper.OrderMapper;
import com.example.shardingjdbctest.rpc.GtcTestClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GtcTestClient gtcTestClient;

    // https://github.com/apache/shardingsphere/issues/10105
    // rewrite sql error when using keyGenerator and encrypt together
    @PostMapping("/insert_email")
    @Transactional
    public Order insert(@RequestParam(name = "email", required = false) String email) {
        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        if (email == null) {
            email = "22";
        }
        orderMapper.select(2L, "1");
        orderMapper.insert(1L, "xaa111111", email);
        orderMapper.insert(2L, "xaa222222", email);
        orderMapper.insert(1L, "xaa333333", email);
//        orderMapper.insert1(1L, "4aaa444444", email);

        if ("error".equals(email)) {
            try {
                orderMapper.insert1(1L, "x4444444", email);
            } catch (Throwable e) {
                System.out.println("------------------------------------------->");
            }
        }
        return order;
    }

    @PostMapping("/insert_email_no")
    public Order insertNo(@RequestParam(name = "email", required = false) String email) {
        Order order = new Order();
        order.setMemberId(1L);
        order.setBillno("1");
        if (email == null) {
            email = "22";
        }
        orderMapper.select(2L, "1");
        orderMapper.insert(1L, "x111111", email);
        orderMapper.insert(2L, "x222222", email);
        orderMapper.insert(1L, "x333333", email);
        orderMapper.insert(1L, "xw33333", email);
        orderMapper.insert(1L, "x5533333", email);

        if ("error".equals(email)) {
            orderMapper.insert1(1L, "x4444444", email);
        }
        return order;
    }

    @GetMapping("/successTest")
    @GlobalTxStart(business = "gtc-test-success", shardingValue = "#shardingValue")
    @Transactional
    public Response<String> gtcTest(String name, long memberId, String shardingValue) {
        if (StringUtils.isBlank(name)) {
            name = "null";
        }
        orderMapper.insert(memberId, "1", name);
        gtcTestClient.gtcTest(name);
        return Response.buildSuccessInfo("gtcTest");
    }

    @GetMapping("/successTestSelfRefresh")
    @GlobalTxStart(business = "gtc-test-success", shardingValue = "#shardingValue", autoFlush = false)
    @Transactional
    public Response<String> gtcTestSelfRefresh(String name, long memberId, String shardingValue) {
        if (StringUtils.isBlank(name)) {
            name = "null";
        }
        orderMapper.insert(memberId, "1", name);
        gtcTestClient.gtcTest(name);
        GlobalTxSpringContextHolder.flushGlobalTxStatus();
        return Response.buildSuccessInfo("gtcTest");
    }

    @GetMapping("/successTestNoSharding")
    @GlobalTxStart(business = "gtc-test-success")
    @Transactional
    public Response<String> gtcTestNoSharding(String name, long memberId) {
        if (StringUtils.isBlank(name)) {
            name = "null";
        }
        orderMapper.insert(memberId, "1", name);
        gtcTestClient.gtcTest(name);
        return Response.buildSuccessInfo("gtcTest");
    }
}
