package org.m1a2st.aop.framework;

import org.m1a2st.aop.AdvisedSupport;

/**
 * @Author m1a2st
 * @Date 2023/7/16
 * @Version v1.0
 */
public class AnotherAopProxy implements AopProxy {

    private final AdvisedSupport advisedSupport;

    public AnotherAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        // TODO other Aop proxy
        return null;
    }
}
