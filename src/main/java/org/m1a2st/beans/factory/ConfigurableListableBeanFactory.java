package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.config.AutowireCapableBeanFactory;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.ConfigurableBeanFactory;

/**
 * @Author m1a2st
 * @Date 2023/7/3
 * @Version v1.0
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 根據名稱查找BeanDefinition
     *
     * @param beanName 名稱
     * @return BeanDefinition
     * @throws BeansException 如果找不到BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
