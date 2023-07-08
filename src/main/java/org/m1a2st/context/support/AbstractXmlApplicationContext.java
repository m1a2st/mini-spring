package org.m1a2st.context.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;
import org.m1a2st.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
