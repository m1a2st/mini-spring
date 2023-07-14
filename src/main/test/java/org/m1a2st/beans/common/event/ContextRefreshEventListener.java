package org.m1a2st.beans.common.event;

import org.m1a2st.context.ApplicationListener;
import org.m1a2st.context.event.ContextRefreshEvent;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class ContextRefreshEventListener implements ApplicationListener<ContextRefreshEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshEvent event) {
        System.out.println(getClass().getName());
    }
}
