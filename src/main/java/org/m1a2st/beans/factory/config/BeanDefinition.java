package org.m1a2st.beans.factory.config;

/**
 * BeanDefinition實例保存bean的信息，包括class類型、
 * 方法構造參數、是否為單例等，此處簡化只包含class類型
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class BeanDefinition {

    private Class<?> beanClass;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }
}
