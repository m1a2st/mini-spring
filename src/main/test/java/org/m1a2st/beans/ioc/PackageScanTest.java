package org.m1a2st.beans.ioc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
public class PackageScanTest {

    @Test
    public void testScanPackage() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        Assertions.assertNotNull(car);
    }
}
