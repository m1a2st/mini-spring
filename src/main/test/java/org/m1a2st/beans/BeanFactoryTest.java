package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public class BeanFactoryTest {

    @Test
    public void test_bean_factory() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);
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
