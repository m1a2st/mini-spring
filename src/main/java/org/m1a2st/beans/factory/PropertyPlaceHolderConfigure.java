package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValue;
import org.m1a2st.beans.PropertyValues;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanFactoryPostProcessor;
import org.m1a2st.core.io.DefaultResourceLoader;
import org.m1a2st.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
public class PropertyPlaceHolderConfigure implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";
    public static final String PLACEHOLDER_SUFFIX = "}";
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties();

        processProperties(beanFactory, properties);
    }

    /**
     * 加載屬性配置文件
     *
     * @return 屬性配置文件
     */
    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof String) {
                String strVal = (String) value;
                StringBuffer sb = new StringBuffer(strVal);
                int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String propKey = strVal.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                    String propVal = properties.getProperty(propKey);
                    sb.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), sb.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
