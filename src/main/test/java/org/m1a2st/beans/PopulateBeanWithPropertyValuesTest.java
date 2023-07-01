package org.m1a2st.beans;

import org.junit.jupiter.api.Test;
import org.m1a2st.BeansException;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.bean.Person;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanReference;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testPopulateBeanWithPropertyValues() throws BeansException {
        // 1. 創建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 創建 BeanDefinition
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "m1a2st"));
        propertyValues.addPropertyValue(new PropertyValue("age", 18));
        // 3. 為 BeanFactory 容器注册 BeanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);
        // 4. 獲取 Bean
        Person person = (Person) beanFactory.getBean("person");
        // 5. 驗證結果
        assertEquals(person.getName(), "m1a2st");
        assertEquals(person.getAge(), 18);
    }

    @Test
    public void testPopulateBeanWithBean() throws BeansException {
        // 1. 創建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2. 創建 Car BeanDefinition
        PropertyValues propertyValuesForCar = new PropertyValues();
        propertyValuesForCar.addPropertyValue(new PropertyValue("brand", "porsche"));
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, propertyValuesForCar);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);
        // 2. 創建 Person BeanDefinition
        PropertyValues propertyValuesForPerson = new PropertyValues();
        propertyValuesForPerson.addPropertyValue(new PropertyValue("name", "m1a2st"));
        propertyValuesForPerson.addPropertyValue(new PropertyValue("age", 18));
        // Person 實例依賴 Car 實例
        propertyValuesForPerson.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValuesForPerson);
        beanFactory.registerBeanDefinition("person", beanDefinition);
        // 3. 獲取 Bean
        Person person = (Person) beanFactory.getBean("person");
        // 4. 驗證結果
        assertEquals(person.getName(), "m1a2st");
        assertEquals(person.getAge(), 18);
        assertEquals(person.getCar().getBrand(), "porsche");
    }
}
