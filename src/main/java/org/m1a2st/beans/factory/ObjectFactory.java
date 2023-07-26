package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;

/**
 * @Author m1a2st
 * @Date 2023/7/26
 * @Version v1.0
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
