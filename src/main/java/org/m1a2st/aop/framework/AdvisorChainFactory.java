package org.m1a2st.aop.framework;

import org.m1a2st.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author m1a2st
 * @Date 2023/7/26
 * @Version v1.0
 */
public interface AdvisorChainFactory {

    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass);
}
