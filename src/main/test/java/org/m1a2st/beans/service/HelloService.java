package org.m1a2st.beans.service;

import org.m1a2st.beans.factory.BeanFactory;
import org.m1a2st.beans.factory.BeanFactoryAware;
import org.m1a2st.context.ApplicationContext;
import org.m1a2st.context.ApplicationContextAware;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class HelloService implements BeanFactoryAware, ApplicationContextAware {

    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    public String sayHello() {
        return "Hello";
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
