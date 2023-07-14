package org.m1a2st.beans.common.event;

import org.m1a2st.context.ApplicationListener;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(getClass().getName());
    }
}
