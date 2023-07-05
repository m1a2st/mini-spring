package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.bean.Person;
import org.m1a2st.beans.common.CustomerBeanPostProcessor;
import org.m1a2st.beans.common.CustomerFactoryPostProcessor;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;
import org.m1a2st.beans.factory.xml.XmlBeanDefinitionReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/5
 * @Version v1.0
 */
public class BeanFactoryPostProcessorAndBeanPostProcessorTest {

    @Test
    public void testBeanFactoryPostProcessor() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 在所有BeanDefinition加載完成後，但在bean實例化之前，提供修改BeanDefinition屬性值的機制
        CustomerFactoryPostProcessor postProcessor = new CustomerFactoryPostProcessor();
        postProcessor.postProcessBeanFactory(beanFactory);

        Person person = (Person) beanFactory.getBean("person");
        assertEquals("ivy", person.getName());
    }

    @Test
    public void testBeanPostProcessor() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 添加bean實例化後的處理器
        CustomerBeanPostProcessor postProcessor = new CustomerBeanPostProcessor();
        beanFactory.addBeanPostProcessor(postProcessor);

        Car car = (Car) beanFactory.getBean("car");
        assertEquals("lamborghini", car.getBrand());
    }
}
