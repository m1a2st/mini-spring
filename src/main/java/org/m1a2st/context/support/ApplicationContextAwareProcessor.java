package org.m1a2st.context.support;

import cn.hutool.core.bean.BeanException;
import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.config.BeanPostProcessor;
import org.m1a2st.context.ApplicationContext;
import org.m1a2st.context.ApplicationContextAware;

/**
 * @Author m1a2st
 * @Date 2023/7/11
 * @Version v1.0
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException, BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
