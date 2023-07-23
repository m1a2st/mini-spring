package org.m1a2st.beans.ioc;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.bean.Person;
import org.m1a2st.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author m1a2st
 * @Date 2023/7/22
 * @Version v1.0
 */
public class AutowiredAnnotationTest {

    @Test
    public void testAutowiredAnnotation() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");
        Person person = applicationContext.getBean(Person.class);
        assertNotNull(person.getCar());
    }
}
