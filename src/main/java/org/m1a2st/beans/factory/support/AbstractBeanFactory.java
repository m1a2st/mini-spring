package org.m1a2st.beans.factory.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) getBean(beanName);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
