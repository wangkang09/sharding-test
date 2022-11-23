//
//package cn.dotfashion.soa.tc.client.aop;
//
//import cn.dotfashion.soa.tc.client.GlobalTxSpringContextHolder;
//import cn.dotfashion.soa.tc.client.annotation.GlobalLock;
//import cn.dotfashion.soa.tc.client.annotation.GlobalTxStart;
//import cn.dotfashion.soa.tc.client.core.DefaultGlobalTransaction;
//import cn.dotfashion.soa.tc.client.core.GlobalTxContext;
//import cn.dotfashion.soa.tc.client.core.exception.FlushTxStatusException;
//import cn.dotfashion.soa.tc.client.core.vo.GlobalLockVo;
//import cn.dotfashion.soa.tc.client.core.vo.GlobalTxVo;
//import cn.dotfashion.soa.tc.client.util.AnnotationUtils;
//import cn.dotfashion.soa.tc.client.util.ParamParserUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.context.expression.MethodBasedEvaluationContext;
//import org.springframework.core.DefaultParameterNameDiscoverer;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.core.env.Environment;
//import org.springframework.expression.EvaluationContext;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.transaction.support.TransactionSynchronizationAdapter;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//import java.lang.reflect.Method;
//
//import static cn.dotfashion.soa.tc.client.GlobalTxSpringContextHolder.GLOBAL_TX_HOLDER;
//import static cn.dotfashion.soa.tc.client.annotation.GlobalTxStart.ENV_TAG;
//import static cn.dotfashion.soa.tc.client.annotation.GlobalTxStart.SYSTEM_NAME;
//
//
///**
// * @author wangkang
// * @date 2020-08-10
// * @since -
// */
//@Slf4j
//public class GlobalTxInterceptor implements MethodInterceptor {
//
//    private final Environment environment;
//    /**
//     * 参数发现器
//     */
//    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
//    /**
//     * Express语法解析器
//     */
//    private static final ExpressionParser PARSER = new SpelExpressionParser();
//
//    public GlobalTxInterceptor(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//
//        GlobalTxStart gtx = AnnotationUtils.getAnnotation(invocation, GlobalTxStart.class);
//        GlobalTxVo globalTxVo = setGlobalTx(gtx, invocation);
//
//        GlobalLock globalLock = AnnotationUtils.getAnnotation(invocation, GlobalLock.class);
//        GlobalLockVo globalLockVo = setGlobalLock(globalLock);
//
//        DefaultGlobalTransaction globalTransaction = (DefaultGlobalTransaction) GlobalTxContext.createGlobalTx(globalTxVo, globalLockVo);
//        globalTransaction.setApiMode(!gtx.autoFlush());
//
//        try {
//            GLOBAL_TX_HOLDER.set(globalTransaction);
//            GlobalTxSpringContextHolder.setShardingValue(getShardingValue(gtx.shardingValue(), invocation));
//
//            globalTransaction.begin();
//            //业务失败，就不会 commit
//            Object result = invocation.proceed();
//            //全局事务完成之后，判断当前有没有本地事务，如果有则等到本地事务执行完在 commit
//            if (TransactionSynchronizationManager.isSynchronizationActive()) {
//                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                    @Override
//                    public void afterCommit() {
//                        //判断本地事务是刷新状态
//                        if (!globalTransaction.txFlushed()) {
//                            throw new FlushTxStatusException("<GTC-SDK>全局事务内没有刷新本地事务状态，method:" + invocation.getMethod().toString());
//                        }
//                        globalTransaction.commit("正常提交");
//                    }
//                });
//            } else {
//                //判断本地事务是刷新状态
//                if (!globalTransaction.txFlushed()) {
//                    throw new FlushTxStatusException("<GTC-SDK>全局事务内没有刷新本地事务状态，method:" + invocation.getMethod().toString());
//                }
//                globalTransaction.commit("正常提交");
//            }
//            return result;
//        } catch (Throwable throwable) {
//            //能走到这里有两种情况：1）Fail-fast tc 异常 2）业务异常。这两种情况都要回滚
//            boolean canRollback = canRollback(throwable, gtx.rollbackFor(), gtx.noRollbackFor());
//            if (canRollback) {
//                //tc 无异常 或 fail-fast 并且符合回滚要求，就回滚。这里可能 手动 flush 了。
//                globalTransaction.rollback();
//            } else {
//                //这里如果因为业务异常没有刷新状态，会报 warning。如果中间件已有开启事务信息，但是提交失败，会查业务数据库，可能有问题
//                globalTransaction.commit("异常提交，因为不满足回滚条件");
//            }
//            throw throwable;
//        } finally {
//            if (gtx.autoFlush() && TransactionSynchronizationManager.isSynchronizationActive()) {
//                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                    @Override
//                    public void afterCompletion(int status) {
//                        //不管提交还是回滚都清理threadlocal
//                        GLOBAL_TX_HOLDER.remove();
//                    }
//                });
//            } else {
//                GLOBAL_TX_HOLDER.remove();
//            }
//        }
//    }
//
//    private String getShardingValue(String shardingValue, MethodInvocation invocation) {
//        if ("".equals(shardingValue)) {
//            return "";
//        }
//        Method method = invocation.getMethod();
//        Object[] arguments = invocation.getArguments();
//        EvaluationContext context = new MethodBasedEvaluationContext(null, method, arguments, NAME_DISCOVERER);
//        final Object value = PARSER.parseExpression(shardingValue).getValue(context);
//        return value == null ? "" : value.toString();
//    }
//
//    protected boolean canRollback(Throwable throwable, Class<? extends Throwable>[] rollbackFor, Class<? extends Throwable>[] notRollbackFor) {
//        for (Class<? extends Throwable> aClass : notRollbackFor) {
//            if (aClass.isAssignableFrom(throwable.getClass())) {
//                return false;
//            }
//        }
//        for (Class<? extends Throwable> aClass : rollbackFor) {
//            if (aClass.isAssignableFrom(throwable.getClass())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private GlobalTxVo setGlobalTx(GlobalTxStart gtx, MethodInvocation invocation) {
//        GlobalTxVo globalTxVo = new GlobalTxVo();
//        String business = environment.getProperty(gtx.business());
//        if (StringUtils.isNotEmpty(business)) {
//            globalTxVo.setBusiness(business);
//        } else {
//            globalTxVo.setBusiness(gtx.business());
//        }
//
//        String systemEl = gtx.systemEl();
//        if (SYSTEM_NAME.equals(systemEl)) {
//            systemEl = environment.getProperty(systemEl);
//            if (StringUtils.isBlank(systemEl)) {
//                throw new IllegalArgumentException("<GTC-SDK>系统名不能为空，请配置global-tx.system-name参数！");
//            }
//        }
//        globalTxVo.setSystem(systemEl);
//
//        globalTxVo.setTxTimeoutSeconds(gtx.txTimeoutSeconds());
//        globalTxVo.setTxFailMode(gtx.txTailMode());
//        globalTxVo.setRequestTimeoutMs(gtx.requestTimeoutMs());
//
//        String envTag = gtx.envTagEl();
//        if (ENV_TAG.equals(envTag)) {
//            envTag = environment.getProperty(envTag);
//        }
//        globalTxVo.setEnvTag(envTag);
//
//        String bizFlag = gtx.bizFlag();
//        if (StringUtils.isNotBlank(bizFlag)) {
//            bizFlag = ParamParserUtil.parser(invocation.getMethod(), invocation.getArguments(), bizFlag);
//        }
//        globalTxVo.setBizFlag(bizFlag);
//        return globalTxVo;
//    }
//
//    private GlobalLockVo setGlobalLock(GlobalLock globalLock) {
//        GlobalLockVo globalLockVo = null;
//
//        if (globalLock != null) {
//            globalLockVo = new GlobalLockVo();
//            globalLockVo.setLockExpireMilliseconds(globalLock.lockExpireMilliseconds());
//            globalLockVo.setLockKey(globalLock.lockKey());
//            globalLockVo.setLockRequester(globalLock.lockRequester());
//            globalLockVo.setWaitLockTimeoutMilliseconds(globalLock.waitLockTimeoutMilliseconds());
//        }
//
//        return globalLockVo;
//    }
//}