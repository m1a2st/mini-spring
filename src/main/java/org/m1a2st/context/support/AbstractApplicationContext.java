package org.m1a2st.context.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.ConfigurableListableBeanFactory;
import org.m1a2st.beans.factory.config.BeanFactoryPostProcessor;
import org.m1a2st.beans.factory.config.BeanPostProcessor;
import org.m1a2st.context.ConfigurableApplicationContext;
import org.m1a2st.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 創建BeanFactory，並加載BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 在Bean實例化之前，執行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor需要提前與其他Bean實例化之前註冊
        registerBeanPostProcessors(beanFactory);

        // 提前實例化單例Bean
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 注册BeanPostProcessor
     *
     * @param beanFactory bean工廠
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 在Bean實例化之前，執行BeanFactoryPostProcessor
     *
     * @param beanFactory bean工廠
     * @throws BeansException Bean處理異常
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 創建BeanFactory，並加載BeanDefinition
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

}
