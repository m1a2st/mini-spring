package org.m1a2st.aop.framework;

import org.m1a2st.aop.AdvisedSupport;

/**
 * @Author m1a2st
 * @Date 2023/7/16
 * @Version v1.0
 */
public class ProxyFactory extends AdvisedSupport {

    private AdvisedSupport advisedSupport;

    public ProxyFactory() {
    }

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass() || getTargetSource().getTargetClass().length == 0) {
            return new AnotherAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
