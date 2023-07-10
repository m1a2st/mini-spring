package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

/**
 * @Author m1a2st
 * @Date 2023/7/10
 * @Version v1.0
 */
public class InitAndDestroyMethodTest {

    @Test
    public void testInitAndDestroyMethod() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        applicationContext.registryShutdownHook();
    }
}
