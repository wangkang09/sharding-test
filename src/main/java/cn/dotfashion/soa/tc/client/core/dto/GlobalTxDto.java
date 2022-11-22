//package cn.dotfashion.soa.tc.client.core.dto;
//
//import cn.dotfashion.soa.tc.client.core.TxFailMode;
//import lombok.Data;
//import lombok.ToString;
//import org.hibernate.validator.constraints.Range;
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
//public class GlobalTxDto {
//    /**
//     * 全局事务唯一 id，用于区分多次获取全局事务
//     */
//    private String uuid;
//    /**
//     * 全局事务 id
//     */
//    private String globalTxId;
//    /**
//     * 系统名
//     */
//    private String system;
//    /**
//     * 业务名
//     */
//    private String business;
//    /**
//     * 分布式事务失败模式
//     */
//    private TxFailMode txFailMode;
//
//    /**
//     * 用于提交，备注提交原因
//     */
//    @ToString.Exclude
//    private String remark;
//    /**
//     * 业务标签
//     */
//    private String bizFlag;
//
//    /**
//     * 分布式事务超时时间
//     */
//    @Range(
//            min = 1L,
//            max = 1800L,
//            message = "超时时间必须在30分钟内"
//    )
//    @NotNull
//    private Integer txTimeoutSeconds = DEFAULT_TX_TIMEOUT_SECONDS;
//
//    private String lockKey;
//    private String lockRequester;
//    private long lockExpireMilliseconds;
//    private long waitLockTimeoutMilliseconds;
//
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
//    //--------------- 以下是分布式事务发起方的回滚接口定义
//
//    private RollbackData rollbackData;
//
//    private long txStartTime;
//
//    /**
//     * 本地全局事务表所在库Id
//     */
//    private Integer shardingDbId;
//
//    @Data
//    public static class RollbackData {
//        /**
//         * 分支事务类型
//         */
//        private String type = "RPC";
//        /**
//         * 分支事务回滚接口标签，用于区分接口
//         */
//        private String apiTag;
//        /**
//         * 分支事务回滚接口路径
//         */
//        private String rollbackPath;
//        private String httpMethod;
//        private Map<String, String> header;
//        /**
//         * 分支事务回滚请求体
//         */
//        private String bodyJson;
//    }
//}
