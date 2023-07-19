package org.m1a2st.expanding;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
public class PropertyPlaceHolderConfigurerTest {

    @Test
    public void testPropertyPlaceHolder() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertEquals("toyota", car.getBrand());
    }
}
