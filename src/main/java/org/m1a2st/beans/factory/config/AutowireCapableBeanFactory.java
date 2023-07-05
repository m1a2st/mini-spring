package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.BeanFactory;

/**
 * @Author m1a2st
 * @Date 2023/7/3
 * @Version v1.0
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 執行 BeanPostProcessor的postProcessBeforeInitialization方法
     *
     * @param existingBean 當前bean
     * @param beanName     bean名稱
     * @return 修改後的bean
     * @throws BeansException Bean處理異常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    /**
     * 執行 BeanPostProcessor的postProcessAfterInitialization方法
     *
     * @param existingBean 當前bean
     * @param beanName     bean名稱
     * @return 修改後的bean
     * @throws BeansException Bean處理異常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;
}
