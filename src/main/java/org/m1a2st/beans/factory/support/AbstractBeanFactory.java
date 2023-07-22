package org.m1a2st.beans.factory.support;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.FactoryBean;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.config.BeanPostProcessor;
import org.m1a2st.beans.factory.config.ConfigurableBeanFactory;
import org.m1a2st.context.util.StringValueResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null) {
            // 如果是FactoryBean，從FactoryBean#Object中創建bean
            return getObjectForBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);
        return getObjectForBeanInstance(bean, beanName);
    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) throws BeansException {
        Object object = beanInstance;
        if (beanInstance instanceof FactoryBean) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    // singleton作用與bean，從緩存中獲取
                    object = factoryBeanObjectCache.get(beanName);
                    if (object == null) {
                        object = factoryBean.getObject();
                        factoryBeanObjectCache.put(beanName, object);
                    }
                } else {
                    // prototype作用域bean，新創建bean
                    object = factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return object;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return (T) getBean(beanName);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 有則覆蓋
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
