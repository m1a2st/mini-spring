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
     * @throws BeansException BeansException
     */
    void refresh() throws BeansException;

    /**
     * 關閉應用上下文
     */
    void close();

    /**
     * 向虛擬機中註冊一個鉤子方法，在虛擬機關閉之前執行關閉容器等操作
     */
    void registryShutdownHook();
}
