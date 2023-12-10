package org.m1a2st.beans.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.m1a2st.aop.AdvisedSupport;
import org.m1a2st.aop.ClassFilter;
import org.m1a2st.aop.TargetSource;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.m1a2st.aop.framework.JdkDynamicAopProxy;
import org.m1a2st.aop.framework.ProxyFactory;
import org.m1a2st.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.m1a2st.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.m1a2st.beans.common.WorldServiceAfterReturnAdvice;
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
        String expression = "execution(* org.m1a2st.beans.service.WorldService.*(..))";
        AspectJExpressionPointcutAdvisor advisor =
                new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        AfterReturningAdviceInterceptor methodInterceptor =
                new AfterReturningAdviceInterceptor(new WorldServiceAfterReturnAdvice());
        advisor.setAdvice(methodInterceptor);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(interceptor);
        advisedSupport.addAdvisor(advisor);
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
        WorldService proxy = (WorldService) new ProxyFactory().getProxy();
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

    @Test
    public void testAdvisor() throws Exception {
        WorldServiceImpl worldService = new WorldServiceImpl();

        // Advisor == Pointcut + Advice
        String expression = "execution(* org.m1a2st.beans.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(interceptor);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(worldService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
            proxy.explode();
        }
    }
}
