package org.m1a2st.aop.framework;

import org.m1a2st.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 獲取目標對象
        Object target = advised.getTargetSource().getTarget();
        Class<?> targetClazz = target.getClass();
        Object retVal;
        // 獲取攔截器鏈
        List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClazz);
        if (chain == null || chain.isEmpty()) {
            return method.invoke(target, args);
        } else {
            // 將攔截器統一封裝成ReflectiveMethodInvocation
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClazz, chain);
            // 依次執行每個攔截器的invoke方法
            retVal = invocation.proceed();
        }
        return retVal;
    }

    /**
     * 返回代理对象
     *
     * @return 代理对象
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }
}
