package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;

/**
 * bean 容器
 *
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public interface BeanFactory {

    /**
     * 根據bean名稱獲取bean
     *
     * @param beanName bean名稱
     * @return bean
     * @throws BeansException 當bean不存在時
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 根據bean名稱和類型獲取bean
     *
     * @param beanName     bean名稱
     * @param requiredType bean類型
     * @param <T>          bean類型
     * @return bean
     * @throws BeansException 當bean不存在時
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;
}
