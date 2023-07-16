package org.m1a2st.beans.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.m1a2st.aop.AdvisedSupport;
import org.m1a2st.aop.MethodMatcher;
import org.m1a2st.aop.TargetSource;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcut;
import org.m1a2st.aop.framework.JdkDynamicAopProxy;
import org.m1a2st.aop.framework.ProxyFactory;
import org.m1a2st.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.m1a2st.beans.common.WorldServiceBeforeAdvice;
import org.m1a2st.beans.common.WorldServiceInterceptor;
import org.m1a2st.beans.service.WorldService;
import org.m1a2st.beans.service.WorldServiceImpl;

/**
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class DynamicProxyTest {

    private AdvisedSupport advisedSupport;

    @BeforeEach
    public void setup() {
        WorldService worldService = new WorldServiceImpl();

        advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor interceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher =
                new AspectJExpressionPointcut("execution(* org.m1a2st.beans.service.WorldService.*(..))")
                        .getMethodMatcher();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(interceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    @Test
    public void testJdkDynamicProxy() {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory() {
        // 使用JDK動態代理
        advisedSupport.setProxyTargetClass(false);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
        // 使用不同的代理
//        advisedSupport.setProxyTargetClass(true);
//        proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
//        proxy.explode();
    }

    @Test
    public void testBeforeAdvice() {
        WorldServiceBeforeAdvice beforeAdvice = new WorldServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(interceptor);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }
}
