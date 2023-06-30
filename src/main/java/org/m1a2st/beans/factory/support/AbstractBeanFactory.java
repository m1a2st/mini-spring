package org.m1a2st.beans.factory.support;

import org.m1a2st.BeansException;
import org.m1a2st.beans.BeanFactory;
import org.m1a2st.beans.factory.config.BeanDefinition;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
