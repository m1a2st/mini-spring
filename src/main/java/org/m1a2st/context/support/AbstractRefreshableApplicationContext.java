package org.m1a2st.context.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 創建BeanFactory，並加載BeanDefinition
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 加載BeanDefinition
     *
     * @param beanFactory bean工廠
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    /**
     * 創建bean工廠
     *
     * @return bean工廠
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
