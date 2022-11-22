//package com.example.shardingjdbctest;
//
//import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author wangkang
// * @version 0.0.1
// * @since 2022-11-17
// */
//@Configuration
//public class ShardingSphereRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        String className = ShardingSphereDataSource.class.getName();
//        String beanName = "shardingSphereDataSource";
//        if (registry.containsBeanDefinition(beanName)) {
//            BeanDefinition shardingSphereDataSource = registry.getBeanDefinition(beanName);
//            registry.removeBeanDefinition(beanName);
//            registry.registerBeanDefinition(className + AutowireCapableBeanFactory.ORIGINAL_INSTANCE_SUFFIX, shardingSphereDataSource);
//            return;
//        }
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//    }
//}