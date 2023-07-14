package org.m1a2st.context;

import java.util.EventListener;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 處理事件
     *
     * @param event 事件
     */
    void onApplicationEvent(E event);
}
