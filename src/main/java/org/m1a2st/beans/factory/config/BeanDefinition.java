package org.m1a2st.beans.factory.config;

import org.m1a2st.beans.PropertyValues;

/**
 * BeanDefinition實例保存bean的信息，包括class類型、
 * 方法構造參數、是否為單例等，
 * 此處簡化只包含class類型跟bean屬性，初始方法名及銷毀方法名
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class BeanDefinition {

    private Class<?> beanClass;
    private PropertyValues propertyValues;
    private String initMethodName;
    private String destroyMethodName;

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
