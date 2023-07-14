package org.m1a2st.context.event;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.BeanFactory;
import org.m1a2st.context.ApplicationEvent;
import org.m1a2st.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author m1a2st
 * @Date 2023/7/13
 * @Version v1.0
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportEvent(applicationListener, event)) {
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 監聽器是否對該事件有興趣
     *
     * @param applicationListener 監聽器
     * @param event               事件
     * @return 是否有興趣
     */
    protected boolean supportEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // type是ApplicationListener的泛型參數
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        // 取得泛型參數的實際類型
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        // 取得實際類型的類名
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
