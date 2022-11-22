package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class GtcDbComplexAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Collection<String> list = getShardingValue(shardingValue.getColumnNameAndShardingValuesMap(), "sharding_value");
        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("");
        }
        Collection<String> resultList = new LinkedHashSet<>();

        list.forEach(str -> {
            if (str == null || "".equals(str)) {
                throw new RuntimeException("");
            } else {
                if (str.length() % 2 == 0) {
                    resultList.add("db0");
                } else {
                    resultList.add("db1");
                }
            }
        });
        return resultList;
    }

    private static <T> Collection<T> getShardingValue(Map<String, Collection<Comparable<?>>> shardingMap, final String key) {
        return (Collection<T>) shardingMap.get(key);
    }

    @Override
    public void init() {

    }

    /**
     * Get type.
     *
     * @return type
     */
    @Override
    public String getType() {
        return "gtcDbComplex";
    }

    /**
     * Get properties.
     *
     * @return properties
     */
    @Override
    public Properties getProps() {
        return null;
    }

    /**
     * Set properties.
     *
     * @param props properties
     */
    @Override
    public void setProps(Properties props) {

    }
}
