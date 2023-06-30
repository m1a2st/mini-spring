package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;
import org.m1a2st.beans.factory.support.SimpleInstantiationStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public class BeanFactoryTest {

    @Test
    public void testBeanFactory_withSimpleInstantiationStrategy() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(new SimpleInstantiationStrategy());
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertNotNull(helloService);
        assertEquals(helloService.sayHello(), "Hello");
    }

    @Test
    public void testBeanFactory_withCglibSubclassingInstantiationStrategy() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(new CglibSubclassingInstantiationStrategy());
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
