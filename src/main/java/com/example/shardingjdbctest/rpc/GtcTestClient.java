package com.example.shardingjdbctest.rpc;

import cn.dotfashion.soa.api.vo.Response;
import cn.dotfashion.soa.restypass.annotation.RestyMethod;
import cn.dotfashion.soa.restypass.annotation.RestyService;
import cn.dotfashion.soa.restypass.filter.signature.ExcludeAuthSignature;
import cn.dotfashion.soa.restypass.filter.signature.RestyFilterAuthBySignature;
import cn.dotfashion.soa.tc.client.annotation.BranchTxRpcStart;
import com.example.shardingjdbctest.entity.UserVo;
import com.example.shardingjdbctest.entity.UserVo1;
import com.example.shardingjdbctest.entity.UserVo2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangkang
 * @date 2021-08-31
 * @since -
 */
@RestyService
@RestyFilterAuthBySignature
public interface GtcTestClient {
    @GetMapping("/gtcRollbackTest")
    @BranchTxRpcStart
    Response<String> gtcTestRollback(@RequestParam("name") String name);

    @GetMapping("/gtcSuccessTest")
    @BranchTxRpcStart
    Response<String> gtcTest(@RequestParam("name") String name);

    @GetMapping("/gtcSuccessTest")
    @BranchTxRpcStart
    @ExcludeAuthSignature
    Response<String> gtcTestNoCipherTest(@RequestParam("name") String name);

    @GetMapping("/gtcMultiBranchTest")
    @BranchTxRpcStart
    Response<String> gtcMultiBranchTest(@RequestParam("name") String name);

    @GetMapping("/badGatewayTest")
    @RestyMethod(retry = 2)
    Response<String> badGatewayTest(@RequestParam("name") String name);

    @GetMapping("/yingShe")
    @BranchTxRpcStart
    Response<String> yingshe(@RequestBody UserVo userVo);

    @GetMapping("/yingShe1")
    @BranchTxRpcStart
    Response<String> yingshe1(@RequestBody UserVo1 userVo);

    @GetMapping("/yingShe2")
    @BranchTxRpcStart
    Response<String> yingshe2(@RequestBody UserVo2 userVo);

    @GetMapping("/huigundaoguaTest")
    @BranchTxRpcStart
    Response<String> huigundaoguaTest(@RequestParam("name") String name);

    @GetMapping("/huigundaogua1Test")
    @BranchTxRpcStart
    @RestyMethod(requestTimeout = 1000)
    Response<String> huigundaogua1Test(@RequestParam("name") String name, @RequestParam("sleep") long time);

    @GetMapping("cache1")
    Response<List<String>> cache1(String key);

    @GetMapping("cache2")
    Response<List<String>> cache2(String key);

    @GetMapping("cache3")
    Response<List<String>> cache3(String key);

    @GetMapping("cache4")
    Response<List<String>> cache4(String key);

    @GetMapping("cache0")
    Response<List<String>> cache0();

    @GetMapping("/huigundaoguaTestDDD")
    @BranchTxRpcStart
    Response<String> huigundaoguaTestDDD(String name);
}
