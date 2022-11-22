package com.example.shardingjdbctest.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Properties;

@Slf4j
public class DbComplexAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Collection<String> dbList = ComplexUtils.getDbOrTableList(availableTargetNames, shardingValue, ComplexUtils.TYPE_DB);
        log.info("========路由的库：" + dbList);
        return dbList;
    }

    @Override
    public void init() {
        System.out.println(1);
    }

    /**
     * Get type.
     *
     * @return type
     */
    @Override
    public String getType() {
        return "dbComplex";
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
        System.out.println(1);
    }
}
