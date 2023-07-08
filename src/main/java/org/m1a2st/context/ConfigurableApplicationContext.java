package org.m1a2st.context;

import org.m1a2st.beans.BeansException;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
