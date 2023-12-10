package org.m1a2st.beans.common;

import org.m1a2st.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @Author m1a2st
 * @Date 2023/7/26
 * @Version v1.0
 */
public class WorldServiceAfterReturnAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("WorldServiceAfterReturnAdvice afterReturning");
    }
}
