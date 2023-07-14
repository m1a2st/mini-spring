package org.m1a2st.aop;

import java.lang.reflect.Method;

/**
 * @Author m1a2st
 * @Date 2023/7/14
 * @Version v1.0
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}
