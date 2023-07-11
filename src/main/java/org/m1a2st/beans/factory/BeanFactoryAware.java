package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;

/**
 * 實現Aware介面，能感知所屬BeanFactory
 *
 * @Author m1a2st
 * @Date 2023/7/11
 * @Version v1.0
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
