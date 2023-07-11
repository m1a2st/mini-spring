package org.m1a2st.beans.common;

import cn.hutool.core.bean.BeanException;
import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.factory.config.BeanPostProcessor;

/**
 * @Author m1a2st
 * @Date 2023/7/5
 * @Version v1.0
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization, beanName: " + beanName);
        // 換品牌
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization, beanName: " + beanName);
        return bean;
    }
}
