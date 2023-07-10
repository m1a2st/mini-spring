package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;

/**
 * @Author m1a2st
 * @Date 2023/7/9
 * @Version v1.0
 */
public interface InitializingBean {

    /**
     * 初始化方法
     *
     * @throws BeansException Exception處理異常
     */
    void afterPropertiesSet() throws BeansException;
}
