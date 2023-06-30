package org.m1a2st.beans.factory.support;

import org.m1a2st.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}
