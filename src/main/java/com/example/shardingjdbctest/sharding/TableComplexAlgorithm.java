package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Properties;

@Slf4j
public class TableComplexAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Collection<String> tableList = ComplexUtils.getDbOrTableList(availableTargetNames, shardingValue, ComplexUtils.TYPE_TABLE);
        log.info("========路由表：" + tableList);
        return tableList;
    }

    /**
     * Initialize algorithm.
     */
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
        return "tableComplex";
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
