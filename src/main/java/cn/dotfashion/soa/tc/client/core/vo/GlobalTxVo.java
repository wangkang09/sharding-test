//package cn.dotfashion.soa.tc.client.core.vo;
//
//import cn.dotfashion.soa.tc.client.core.TxFailMode;
//import lombok.Data;
//import org.hibernate.validator.constraints.Range;
//import org.springframework.http.HttpMethod;
//
//import javax.validation.constraints.NotNull;
//import java.util.Map;
//
//import static cn.dotfashion.soa.tc.client.core.Constant.DEFAULT_TX_TIMEOUT_SECONDS;
//import static cn.dotfashion.soa.tc.client.core.Constant.REQUEST_TIMEOUT_MS;
//
///**
// * @author wangkang
// * @date 2020-07-31
// * @since -
// */
//@Data
//public class GlobalTxVo {
//    /**
//     * 全局事务唯一 id，用于区分多次获取全局事务
//     */
//    private String uuid;
//    /**
//     * 系统名
//     */
//    private String system;
//    /**
//     * 业务名
//     */
//    private String business;
//    /**
//     * 业务标签，用于查询
//     */
//    private String bizFlag;
//
//    /**
//     * 分布式事务超时时间。默认为 DEFAULT_TX_TIMEOUT_SECONDS。可取区间：1-1800s
//     */
//    @Range(
//            min = 1L,
//            max = 1800L,
//            message = "超时时间必须在30分钟内"
//    )
//    @NotNull
//    private Integer txTimeoutSeconds = DEFAULT_TX_TIMEOUT_SECONDS;
//
//    private TxFailMode txFailMode = TxFailMode.FAIL_FAST;
//    /**
//     * 注册全局、分支事务的远程调用超时时间
//     */
//    private int requestTimeoutMs = REQUEST_TIMEOUT_MS;
//
//    /**
//     * 环境区分（用于非生产环境）
//     */
//    private String envTag;
//
//
//    //--------------- 以下是分布式事务发起方的回滚接口定义
//
//    private RollbackData rollbackData;
//
//    /**
//     * 本地全局事务表所在库Id
//     */
//    private Integer shardingDbId;
//
//    /**
//     * 全局事务的回滚接口，使用 本地事务回滚接口来替代 @BranchTxLocalStart
//     */
//    @Data
//    @Deprecated
//    public static class RollbackData {
//        /**
//         * 分支事务类型
//         */
//        private String type = "RPC";
//        /**
//         * 暂未开放，后期可能用于版本控制
//         */
//        private String apiTag;
//        /**
//         * 分支事务回滚接口路径。如 /test/tc
//         */
//        private String rollbackPath;
//        /**
//         * 请求类型，如 POST,GET
//         */
//        private String httpMethod = HttpMethod.POST.name();
//        private Map<String, String> header;
//        /**
//         * 分支事务回滚请求体，json 字符串形式
//         */
//        private String bodyJson;
//    }
//}
