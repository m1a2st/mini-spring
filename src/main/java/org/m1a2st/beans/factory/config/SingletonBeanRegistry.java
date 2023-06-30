package org.m1a2st.beans.factory.config;

/**
 * 單例註冊表
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
