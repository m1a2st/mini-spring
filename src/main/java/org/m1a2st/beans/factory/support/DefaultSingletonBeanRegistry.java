package org.m1a2st.beans.factory.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.DisposableBean;
import org.m1a2st.beans.factory.ObjectFactory;
import org.m1a2st.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultSingletonBeanRegistry的職責為
 * 1. 管理單例bean
 * 2. 註冊單例bean
 * 3. 管理單例bean的銷毀
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 二級緩存
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();
    // 一級緩存
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 三級緩存
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    // 這裡的singletonObject是一個ObjectFactory，而不是bean實例
                    singletonObject = singletonFactory.getObject();
                    // 從三級緩存放進二級緩存
                    earlySingletonObjects.put(beanName, singletonObject);
                    // 從三級緩存移除
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        singletonFactories.put(beanName, singletonFactory);
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        ArrayList<String> beanNames = new ArrayList<>(disposableBeans.keySet());
        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
