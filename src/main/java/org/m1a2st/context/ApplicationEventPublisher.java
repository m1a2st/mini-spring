package org.m1a2st.context;

/**
 * 事件發佈介面
 *
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public interface ApplicationEventPublisher {

    /**
     * 發佈事件
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);
}
