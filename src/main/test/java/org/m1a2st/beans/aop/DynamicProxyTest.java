package org.m1a2st.beans.aop;

import org.junit.jupiter.api.Test;
import org.m1a2st.aop.AdvisedSupport;
import org.m1a2st.aop.MethodMatcher;
import org.m1a2st.aop.TargetSource;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcut;
import org.m1a2st.aop.framework.JdkDynamicAopProxy;
import org.m1a2st.beans.common.WorldServiceInterceptor;
import org.m1a2st.beans.service.WorldService;
import org.m1a2st.beans.service.WorldServiceImpl;

/**
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class DynamicProxyTest {

    @Test
    public void testJdkDynamicProxy() {
        WorldService worldService = new WorldServiceImpl();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor interceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher =
                new AspectJExpressionPointcut("execution(* org.m1a2st.beans.service.WorldService.*(..))")
                        .getMethodMatcher();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(interceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }
}
