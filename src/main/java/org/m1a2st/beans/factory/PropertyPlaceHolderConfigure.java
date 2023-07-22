package org.m1a2st.beans.factory;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValue;
import org.m1a2st.beans.PropertyValues;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanFactoryPostProcessor;
import org.m1a2st.context.util.StringValueResolver;
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
        // 加載屬性配置文件
        Properties properties = loadProperties();
        // 屬性值替換佔位符
        processProperties(beanFactory, properties);
        // 往容器添加StringValueResolver，用於解析@Value注解
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
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
                value = resolvePlaceholder((String) value, properties);
                propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
            }
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        // 僅支持一個佔位符的格式
        StringBuilder buf = new StringBuilder(value);
        int startIndex = value.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = value.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = value.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buf.replace(startIndex, endIndex + 1, propVal);
        }
        return buf.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }


    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) throws BeansException {
            return PropertyPlaceHolderConfigure.this.resolvePlaceholder(strVal, properties);
        }
    }
}
