package org.m1a2st.context.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.ConfigurableListableBeanFactory;
import org.m1a2st.beans.factory.config.BeanFactoryPostProcessor;
import org.m1a2st.beans.factory.config.BeanPostProcessor;
import org.m1a2st.context.ApplicationEvent;
import org.m1a2st.context.ApplicationListener;
import org.m1a2st.context.ConfigurableApplicationContext;
import org.m1a2st.context.event.ApplicationEventMulticaster;
import org.m1a2st.context.event.ContextClosedEvent;
import org.m1a2st.context.event.ContextRefreshEvent;
import org.m1a2st.context.event.SimpleApplicationEventMulticaster;
import org.m1a2st.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 創建BeanFactory，並加載BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 添加ApplicationContextAwareProcessor，讓繼承自ApplicationContextAware的bean能感知bean
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在Bean實例化之前，執行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor需要提前與其他Bean實例化之前註冊
        registerBeanPostProcessors(beanFactory);

        // 初始化事件發布者
        initApplicationEventMulticaster();

        // 註冊事件監聽器
        registerListeners();

        // 提前實例化單例Bean
        beanFactory.preInstantiateSingletons();

        // 發布容器刷新完成事件
        finishRefresh();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 發布容器刷新完成事件
     */
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent(this));
    }

    /**
     * 註冊事件監聽器
     */
    private void registerListeners() {
        getBeansOfType(ApplicationListener.class)
                .values()
                .forEach(listener -> applicationEventMulticaster.addApplicationListener(listener));
    }

    /**
     * 初始化事件發佈者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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

    @Override
    public void close() {
        doClose();
    }

    protected void doClose() {
        // 發布容器關閉事件
        publishEvent(new ContextClosedEvent(this));
        // 執行單例Bean的銷毀方法
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public void registryShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::doClose));
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }
}
