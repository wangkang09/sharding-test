//package cn.dotfashion.soa.tc.client.annotation;
//
//import cn.dotfashion.soa.tc.client.IGlobalTxRollback;
//import cn.dotfashion.soa.tc.client.core.TxFailMode;
//
//import java.lang.annotation.*;
//
//import static cn.dotfashion.soa.tc.client.core.Constant.DEFAULT_TX_TIMEOUT_SECONDS;
//import static cn.dotfashion.soa.tc.client.core.Constant.REQUEST_TIMEOUT_MS;
//
///**
// * @author wangkang
// * @date 2020-08-10
// * @since -
// */
//@Target({ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//public @interface GlobalTxStart {
//    String SYSTEM_NAME = "global-tx.system-name";
//    String ENV_TAG = "global-tx.env-tag";
//
//    /**
//     * 业务名
//     */
//    String business();
//
//    /**
//     * 系统名：CMDB 名
//     */
//    String systemEl() default SYSTEM_NAME;
//
//    /**
//     * 业务标签。用于查询
//     *
//     * @return
//     */
//    String bizFlag() default "";
//
//    /**
//     * 分布式事务失败时，是否影响业务
//     */
//    TxFailMode txTailMode() default TxFailMode.FAIL_SAFE;
//
//    /**
//     * 分布式超时时间，超过此时间分布式事务没有提交后，且数据库中没有分布式事务提交状态，则回滚分布式事务
//     */
//    int txTimeoutSeconds() default DEFAULT_TX_TIMEOUT_SECONDS;
//
//    /**
//     * 分布式事务请求超时时间
//     */
//    int requestTimeoutMs() default REQUEST_TIMEOUT_MS;
//
//    /**
//     * 刷新全局事务状态。只有全局事务内只有一个本地事务，且此本地事务在所有的分支事务后面，才可以使用自动模式，否则请使用 <br/>
//     * cn.dotfashion.soa.tc.client.GlobalTxSpringContextHolder#flushGlobalTxStatus() 自动加入本地事务刷新全局事务状态
//     */
//    boolean autoFlush() default true;
//
//    /**
//     * 环境区分（用于非生产环境）
//     */
//    String envTagEl() default ENV_TAG;
//
//    /**
//     * 暂未开放，后期可能用于版本控制
//     */
//    String apiTag() default "";
//
//    /**
//     * 全局事务回滚接口路径，例如：/test/tc。全局事务回滚接口由 本地分支事务回滚接口替代。@BranchTxLocalStart
//     */
//    @Deprecated
//    String rollbackPath() default "";
//
//    /**
//     * 开发实现此接口，填充 header 和 bodyJson 数据，用于全局事务回滚接口。全局事务回滚接口由 本地分支事务回滚接口替代。@BranchTxLocalStart
//     */
//    @Deprecated
//    Class<? extends IGlobalTxRollback> rollbackDataClass() default IGlobalTxRollback.NoOpGlobalTxRollback.class;
//
//    /**
//     * rollbackData() 对应的 class 的实例获取方式。默认先从 spring 容器中获取，取不到再通过无参构造器创建。全局事务回滚接口由 本地分支事务回滚接口替代。@BranchTxLocalStart
//     */
//    @Deprecated
//    IGlobalTxRollback.InstanceMode instanceMode() default IGlobalTxRollback.InstanceMode.SPRING_BEAN_OR_NEW;
//
//    /**
//     * 哪种异常 rollback
//     */
//    Class<? extends Throwable>[] rollbackFor() default {Throwable.class};
//
//    /**
//     * 哪种异常不 rollback
//     */
//    Class<? extends Throwable>[] noRollbackFor() default {};
//
//    /**
//     * shardingjdbc 支持。为 true 时，开启全局事务的时候，SDK 会调用一个方法来获得 全局事务状态信息需要落到的库Id。方法名是：此注解注释的方法名+GenerateShardingDbName后缀，参数和注解方法一致，且在同一个类里
//     *
//     * @return false：不支持。true：支持，本地全局事务状态表分库逻辑。
//     */
//    boolean shardingSupport() default false;
//}
