package org.m1a2st.beans.factory;

/**
 * @Author m1a2st
 * @Date 2023/7/12
 * @Version v1.0
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();
}
