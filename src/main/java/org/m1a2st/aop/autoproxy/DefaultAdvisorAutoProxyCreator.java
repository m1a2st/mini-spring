package org.m1a2st.aop.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.m1a2st.aop.*;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.m1a2st.aop.framework.ProxyFactory;
import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.PropertyValues;
import org.m1a2st.beans.factory.BeanFactory;
import org.m1a2st.beans.factory.BeanFactoryAware;
import org.m1a2st.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.m1a2st.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        // 避免死循環
        if (isInfrastructureClass(bean.getClass())) {
            return null;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(bean.getClass())) {
                    // create proxy object
                    AdvisedSupport advisedSupport = new AdvisedSupport();

                    // set target source
                    advisedSupport.setTargetSource(new TargetSource(bean));
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception ex) {
            throw new BeansException("Error create proxy bean for: " + beanName, ex);
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}

