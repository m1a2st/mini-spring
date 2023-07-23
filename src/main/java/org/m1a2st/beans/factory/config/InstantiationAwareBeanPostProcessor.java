package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValues;

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

    /**
     * 在bean實例化之後執行，設置屬性之前執行
     *
     * @param bean     bean實例
     * @param beanName bean的名稱
     * @return 返回一個boolean值，如果為false，則不會執行後續的bean處理器
     * @throws BeansException bean處理異常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在bean實例化之後執行，設置屬性之前
     *
     * @param pvs      屬性列表
     * @param bean     bean實例
     * @param beanName bean的名稱
     * @return 返回一個屬性列表
     * @throws BeansException bean處理異常
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}
