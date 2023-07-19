package org.m1a2st.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValue;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanReference;
import org.m1a2st.beans.factory.support.AbstractBeanDefinitionReader;
import org.m1a2st.beans.factory.support.BeanDefinitionRegistry;
import org.m1a2st.context.annotation.ClassPathBeanDefinitionScanner;
import org.m1a2st.core.io.Resource;
import org.m1a2st.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author m1a2st
 * @Date 2023/7/4
 * @Version v1.0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                try {
                    doLoadBeanDefinitions(inputStream);
                } catch (DocumentException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }

    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);

        Element root = document.getRootElement();

        // 解析context:component-scan標簽並掃描指定包中的類，提取類信息，組裝成BeanDefinition
        Element componentScan = root.element(COMPONENT_SCAN_ELEMENT);
        if (componentScan != null) {
            String scanPath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements(BEAN_ELEMENT);
        for (Element bean : beanList) {
            String beanId = bean.attributeValue(ID_ATTRIBUTE);
            String beanName = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethodName = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);

            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot find class [" + className + "]");
            }
            //id優先於name
            beanName = StrUtil.isNotEmpty(beanId) ? beanId : beanName;
            if (StrUtil.isEmpty(beanName)) {
                // 如果没有指定beanName，則將beanName設置為類名首字母小寫
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
            for (Element property : propertyList) {
                String propertyNameAttribute = property.attributeValue(NAME_ATTRIBUTE);
                String propertyValueAttribute = property.attributeValue(VALUE_ATTRIBUTE);
                String propertyRefAttribute = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(propertyNameAttribute)) {
                    throw new BeansException("The name attribute cannot be null or empty");
                }

                Object value = propertyValueAttribute;
                if (StrUtil.isNotEmpty(propertyRefAttribute)) {
                    value = new BeanReference(propertyRefAttribute);
                }
                PropertyValue propertyValue = new PropertyValue(propertyNameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                //beanName不能重名
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            //注册BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    /**
     * 掃描指定包下的類，提取類信息，組裝成BeanDefinition
     *
     * @param scanPath 指定包路徑
     */
    private void scanPackage(String scanPath) {
        String[] basePackages = scanPath.split(",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
