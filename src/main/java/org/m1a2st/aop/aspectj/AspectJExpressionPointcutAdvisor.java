package org.m1a2st.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.m1a2st.aop.Pointcut;
import org.m1a2st.aop.PointcutAdvisor;

/**
 * @Author m1a2st
 * @Date 2023/7/17
 * @Version v1.0
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;
    private Advice advice;
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
