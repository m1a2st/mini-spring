package org.m1a2st.aop;

/**
 * 被代理的對象
 *
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
