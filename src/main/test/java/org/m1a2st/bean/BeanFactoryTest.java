package org.m1a2st.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public class BeanFactoryTest {

    @Test
    public void test_bean_factory() {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("helloService", new HelloService());
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertNotNull(helloService);
        assertEquals(helloService.sayHello(), "Hello");
    }

    static class HelloService {
        public String sayHello() {
            return "Hello";
        }
    }
}
