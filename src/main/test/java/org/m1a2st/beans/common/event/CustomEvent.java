package org.m1a2st.beans.common.event;

import org.m1a2st.context.event.ApplicationContextEvent;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class CustomEvent extends ApplicationContextEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CustomEvent(Object source) {
        super(source);
    }
}
