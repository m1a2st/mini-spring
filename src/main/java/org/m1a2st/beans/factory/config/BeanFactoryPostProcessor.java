package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允許自定義修改BeanDefinition的屬性值
 *
 * @Author m1a2st
 * @Date 2023/7/5
 * @Version v1.0
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加載完成後，但在bean實例化之前，提供修改BeanDefinition屬性值的機制
     *
     * @param beanFactory bean工廠
     * @throws BeansException Bean 處理異常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
