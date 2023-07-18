package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.BeansException;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean實例化之前執行
     *
     * @param beanClass bean的class
     * @param beanName  bean的名稱
     * @return 返回一個bean實例
     * @throws BeansException bean處理異常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
