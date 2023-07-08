package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.bean.Person;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        assertEquals("ivy", person.getName());

        Car car = applicationContext.getBean("car", Car.class);
        assertEquals("lamborghini", car.getBrand());
    }
}
