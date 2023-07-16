package org.m1a2st.aop;

import java.lang.reflect.Method;

/**
 * @Author m1a2st
 * @Date 2023/7/16
 * @Version v1.0
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;
}
