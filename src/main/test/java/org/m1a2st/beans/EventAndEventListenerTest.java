package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.common.event.CustomEvent;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class EventAndEventListenerTest {

    @Test
    public void testEventListener() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext));
        // 主動關閉容器
        applicationContext.registryShutdownHook();
    }
}
