package org.m1a2st.beans.ioc;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.A;
import org.m1a2st.beans.bean.B;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/25
 * @Version v1.0
 */
public class CircularReferenceWithoutProxyBeanTest {

    @Test
    public void testCirculateReference() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        assertEquals(a.getB(), b);
    }
}
