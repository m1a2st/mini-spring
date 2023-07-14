package org.m1a2st.beans.common.event;

import org.m1a2st.context.ApplicationListener;
import org.m1a2st.context.event.ContextClosedEvent;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class ContextCloseEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(getClass().getName());
    }
}
