package org.m1a2st.context.event;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class ContextRefreshEvent extends ApplicationContextEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }
}
