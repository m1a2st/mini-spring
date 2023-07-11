package org.m1a2st.context;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.Aware;

/**
 * 標記類介面，實現該介面能感知容器類介面
 *
 * @Author m1a2st
 * @Date 2023/7/11
 * @Version v1.0
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 設置應用上下文
     *
     * @param applicationContext 應用上下文
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
