package org.m1a2st.aop;

import java.lang.reflect.Method;

/**
 * @Author m1a2st
 * @Date 2023/7/26
 * @Version v1.0
 */
public interface AfterReturningAdvice extends AfterAdvice {

    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
