package org.m1a2st.beans.aop;

import org.junit.jupiter.api.Test;
import org.m1a2st.aop.aspectj.AspectJExpressionPointcut;
import org.m1a2st.beans.service.HelloService;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author m1a2st
 * @Date 2023/7/14
 * @Version v1.0
 */
public class PointcutExpressionTest {

    @Test
    public void testPointcutExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* org.m1a2st.beans.service.HelloService.*(..))");
        Class<HelloService> clazz = HelloService.class;
        Method method = clazz.getDeclaredMethod("sayHello");

        assertTrue(pointcut.matches(clazz));
        assertTrue(pointcut.matches(method, clazz));
    }
}
