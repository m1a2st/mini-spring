package org.m1a2st.context.event;

import org.m1a2st.context.ApplicationContext;
import org.m1a2st.context.ApplicationEvent;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
