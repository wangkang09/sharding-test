package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

@Slf4j
public class ComplexUtils {

    public static final int TYPE_DB = 1;
    public static final int TYPE_TABLE = 2;


    /**
     * 获取db策略
     *
     * @param dbOrTableNames
     * @param shardingValue
     * @param type
     * @return
     */
    public static Collection<String> getDbOrTableList(Collection<String> dbOrTableNames, ComplexKeysShardingValue<Comparable<?>> shardingValue, int type) {

        Map<String, Collection<Comparable<?>>> shardingMap = shardingValue.getColumnNameAndShardingValuesMap();
        // 会员id找到的结果集
        Collection<String> memberIdResultList = createWithMemberId(dbOrTableNames, shardingMap, type);
        if (memberIdResultList != null) {
            return memberIdResultList;
        }
        throw new IllegalArgumentException("分库分表sql中传入分片值没有找到对应的表，异常告警");
    }

    private static Collection<String> createWithMemberId(Collection<String> availableTargetNames, Map<String, Collection<Comparable<?>>> shardingMap, int type) {
        Collection<String> resultList = new LinkedHashSet<>();
        Collection<Number> memberIdList = getShardingValue(shardingMap, "member_id");
        if (CollectionUtils.isEmpty(memberIdList)) {
            return null;
        }
        int modValue = type == TYPE_DB ? 2 : 4;
        for (Number num : memberIdList) {
            // db或table已经满了无需再循环解析
            if (isFull(resultList, type)) {
                return resultList;
            }
            // sql中传入的值为空忽略不找表
            if (num == null) {
                continue;
            }

            long shardingValue = num.longValue() % modValue;
            mergeDatas(availableTargetNames, resultList, shardingValue, type);
        }
        return resultList;
    }


    private static boolean isFull(Collection<String> shardignList, int type) {
        if (type == TYPE_DB) {
            if (shardignList.size() == 4) {
                return true;
            }
        } else {
            if (shardignList.size() == 16) {
                return true;
            }
        }
        return false;
    }

    private static void mergeDatas(Collection<String> availableTargetNames, Collection<String> shardignList, long modValue, int type) {

        for (String each : availableTargetNames) {
            // db或table已经满了无需再循环解析
            if (isFull(shardignList, type)) {
                return;
            }

            // 获取DB
            if (type == TYPE_DB) {
                if (each.endsWith(modValue + "")) {
                    shardignList.add(each);
                    break;
                }
            }
            // 获取Table
            else {
                if (each.endsWith("_" + modValue)) {
                    shardignList.add(each);
                    break;
                }
            }

        }
    }

    private static <T> Collection<T> getShardingValue(Map<String, Collection<Comparable<?>>> shardingMap, final String key) {
        return (Collection<T>) shardingMap.get(key);
    }
}
