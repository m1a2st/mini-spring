package org.m1a2st.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author m1a2st
 * @Date 2023/6/29
 * @Version v1.0
 */
public class BeanFactory {

    private Map<String, Object> beanMap = new HashMap<>();

    public void registerBean(String beanName, Object bean) {
        beanMap.put(beanName, bean);
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
