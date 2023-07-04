package org.m1a2st.beans.factory.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.core.io.Resource;
import org.m1a2st.core.io.ResourceLoader;

/**
 * @Author m1a2st
 * @Date 2023/7/3
 * @Version v1.0
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;

    void loadBeanDefinitions(Resource resource) throws BeansException;
}
