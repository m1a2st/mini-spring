package org.m1a2st.beans.factory.config;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class BeanReference {

    public final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
