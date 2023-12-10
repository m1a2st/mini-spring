package org.m1a2st.beans.aop;

import org.junit.jupiter.api.Test;
import org.m1a2st.aop.TargetSource;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.m1a2st.aop.framework.ProxyFactory;
import org.m1a2st.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.m1a2st.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.m1a2st.beans.common.WorldServiceAfterReturnAdvice;
import org.m1a2st.beans.common.WorldServiceBeforeAdvice;
import org.m1a2st.beans.service.WorldService;
import org.m1a2st.beans.service.WorldServiceImpl;

/**
 * @Author m1a2st
 * @Date 2023/7/26
 * @Version v1.0
 */
public class ProxyFactoryTest {

    @Test
    public void testAdvisor() throws Exception{
        WorldService worldService = new WorldServiceImpl();

        // Advisor 是 Advice的子介面，用於封裝 Pointcut 和 Advice 的關係
        String expression = "execution(* org.m1a2st.beans.service.WorldService.explode(..))";
        // 設定切點和通知
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);

        MethodBeforeAdviceInterceptor methodInterceptor =
                new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);

        AspectJExpressionPointcutAdvisor advisor1 = new AspectJExpressionPointcutAdvisor();
        advisor1.setExpression(expression);
        AfterReturningAdviceInterceptor afterReturningAdviceInterceptor=new AfterReturningAdviceInterceptor(new WorldServiceAfterReturnAdvice());
        advisor1.setAdvice(afterReturningAdviceInterceptor);

        ProxyFactory factory = new ProxyFactory();
        TargetSource targetSource = new TargetSource(worldService);
        factory.setTargetSource(targetSource);
        factory.setProxyTargetClass(true);
        factory.addAdvisor(advisor);
        factory.addAdvisor(advisor1);
        WorldService proxy = (WorldService) factory.getProxy();
        proxy.explode();
    }
}
