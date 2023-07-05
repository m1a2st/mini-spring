package org.m1a2st.beans.common;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValue;
import org.m1a2st.beans.PropertyValues;
import org.m1a2st.beans.factory.ConfigurableListableBeanFactory;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @Author m1a2st
 * @Date 2023/7/5
 * @Version v1.0
 */
public class CustomerFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
    }
}
