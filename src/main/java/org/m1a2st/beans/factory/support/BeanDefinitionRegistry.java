package org.m1a2st.beans.factory.support;

import org.m1a2st.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册表介面
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public interface BeanDefinitionRegistry {

    /**
     * 向註冊表註冊BeanDefinition
     *
     * @param beanName       bean名稱
     * @param beanDefinition bean定義
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 是否包含指定名稱的BeanDefinition
     *
     * @param beanName bean名稱
     * @return 是否包含
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回定義所有的 bean名稱
     *
     * @return 所有的 bean名稱
     */
    String[] getBeanDefinitionNames();
}
