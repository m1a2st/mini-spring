package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.service.HelloService;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author m1a2st
 * @Date 2023/7/11
 * @Version v1.0
 */
public class AwareInterfaceTest {

    @Test
    public void testAwareInterface() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-aware.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        assertNotNull(helloService.getApplicationContext());
        assertNotNull(helloService.getBeanFactory());
    }
}
