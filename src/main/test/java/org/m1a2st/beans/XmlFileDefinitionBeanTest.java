package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.bean.Person;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;
import org.m1a2st.beans.factory.xml.XmlBeanDefinitionReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/4
 * @Version v1.0
 */
public class XmlFileDefinitionBeanTest {

    @Test
    public void test_xml_file() throws BeansException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        Person person = (Person) beanFactory.getBean("person");
        assertEquals("m1a2st", person.getName());
        assertEquals("tesla", person.getCar().getBrand());
        Car car = (Car) beanFactory.getBean("car");
        assertEquals("tesla", car.getBrand());
    }
}
