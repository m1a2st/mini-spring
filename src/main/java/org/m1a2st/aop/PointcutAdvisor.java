package org.m1a2st.aop;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
