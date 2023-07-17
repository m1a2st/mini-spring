package org.m1a2st.aop;

import org.aopalliance.aop.Advice;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public interface Advisor {

    Advice getAdvice();
}
