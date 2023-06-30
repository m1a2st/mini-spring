package org.m1a2st.beans.factory.support;

import org.m1a2st.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
