package org.m1a2st.beans.ioc;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class TypeConversionSecondPartTest {

    @Test
    public void testConversionService() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:type-conversion-second-part.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertEquals(car.getPrice(), 1000000);
        assertEquals(car.getProduceTime(), LocalDate.of(2021, 1, 1));
    }
}
