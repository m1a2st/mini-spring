package org.m1a2st.beans;

import org.m1a2st.BeansException;

/**
 * bean 容器
 *
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;
}
