package org.m1a2st.beans.common;

import org.m1a2st.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author m1a2st
 * @Date 2023/7/16
 * @Version v1.0
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) {
        System.out.println("WorldServiceBeforeAdvice do something");
    }
}
