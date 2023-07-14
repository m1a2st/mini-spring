package org.m1a2st.aop;

/**
 * 切點抽象
 *
 * @Author m1a2st
 * @Date 2023/7/14
 * @Version v1.0
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
