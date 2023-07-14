package org.m1a2st.context.event;

import org.m1a2st.beans.factory.BeanFactory;
import org.m1a2st.beans.factory.BeanFactoryAware;
import org.m1a2st.context.ApplicationEvent;
import org.m1a2st.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();
    /**
     * 對象工廠
     */
    private BeanFactory beanFactory;

    /**
     * 添加監聽器
     *
     * @param listener 監聽器
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    /**
     * 移除監聽器
     *
     * @param listener 監聽器
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * 設置對象工廠
     *
     * @param beanFactory 對象工廠
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
