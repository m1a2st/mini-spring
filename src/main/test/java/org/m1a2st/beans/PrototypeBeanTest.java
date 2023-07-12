package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/12
 * @Version v1.0
 */
public class PrototypeBeanTest {

    @Test
    public void testPrototype() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");
        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        assertNotEquals(car1, car2);
    }
}
