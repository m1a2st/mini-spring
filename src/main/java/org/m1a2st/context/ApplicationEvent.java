package org.m1a2st.context;

import java.util.EventObject;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
