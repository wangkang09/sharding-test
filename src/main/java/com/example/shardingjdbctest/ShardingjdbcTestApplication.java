package com.example.shardingjdbctest;

import cn.dotfashion.soa.restypass.spring.EnableRestyPass;
import cn.dotfashion.soa.tc.client.RestyPassTcClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
@EnableRestyPass(basePackageClasses = {ShardingjdbcTestApplication.class, RestyPassTcClient.class})
public class ShardingjdbcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingjdbcTestApplication.class, args);
    }

}
