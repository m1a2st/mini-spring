package org.m1a2st.context.event;

import org.m1a2st.context.ApplicationEvent;
import org.m1a2st.context.ApplicationListener;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加監聽器
     *
     * @param listener 監聽器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除監聽器
     *
     * @param listener 監聽器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 發佈事件
     *
     * @param event 事件
     */
    void multicastEvent(ApplicationEvent event);
}
