package org.m1a2st.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.m1a2st.BeansException;
import org.m1a2st.beans.PropertyValue;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanReference;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    public AbstractAutowireCapableBeanFactory(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    public AbstractAutowireCapableBeanFactory() {
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            // 創造Bean 實體
            bean = createBeanInstance(beanDefinition);
            // 為Bean 填充屬性
            addPropertyValue(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 為Bean 填充屬性
     *
     * @param beanName       bean名稱
     * @param bean           bean實例
     * @param beanDefinition bean定義
     */
    private void addPropertyValue(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 通過反射設置屬性值
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition) throws BeansException {
        return instantiationStrategy.instantiate(beanDefinition);
    }
}
