package org.m1a2st.beans.aop;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.service.WorldService;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public class AutoProxyTest {

    @Test
    public void testAutoProxy() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        // 獲取代理對象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }

    @Test
    public void testPopulateProxyBeanWithPropertyValues() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertEquals("earth", worldService.getName());
    }
}
