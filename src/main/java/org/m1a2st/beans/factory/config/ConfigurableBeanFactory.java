package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.factory.HierarchicalBeanFactory;
import org.m1a2st.context.util.StringValueResolver;

/**
 * @Author m1a2st
 * @Date 2023/7/3
 * @Version v1.0
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 添加BeanPostProcessor
     *
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 銷毀單例bean
     */
    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);
}
