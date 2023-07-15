package org.m1a2st.beans.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class WorldServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("WorldServiceInterceptor#invoke#before#proceed");
        Object result = invocation.proceed();
        System.out.println("WorldServiceInterceptor#invoke#After#proceed");
        return result;
    }
}
