package org.m1a2st.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import org.m1a2st.beans.BeansException;

/**
 * 用於修改實例化後的bean修改擴展點
 *
 * @Author m1a2st
 * @Date 2023/7/5
 * @Version v1.0
 */
public interface BeanPostProcessor {
    /**
     * 在bean實例化之前，提供修改bean屬性值的機制
     *
     * @param bean     bean實例
     * @param beanName bean名稱
     * @return 修改後的bean實例
     * @throws BeanException Bean 處理異常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean實例化之後，提供修改bean屬性值的機制
     *
     * @param bean     bean實例
     * @param beanName bean名稱
     * @return 修改後的bean實例
     * @throws BeanException Bean 處理異常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
