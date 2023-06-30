package org.m1a2st.beans.factory.support;

import org.m1a2st.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Object bean;
        // 通過反射創建bean實例
        try {
            // 獲取無參構造函數
            Constructor<?> constructor = beanClass.getDeclaredConstructor();
            // 設置訪問權限
            constructor.setAccessible(true);
            // 創建bean實例
            bean = constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }
}
