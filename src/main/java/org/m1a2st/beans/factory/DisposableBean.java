package org.m1a2st.beans.factory;

/**
 * @Author m1a2st
 * @Date 2023/7/9
 * @Version v1.0
 */
public interface DisposableBean {

    /**
     * 銷毀方法
     *
     * @throws Exception BeansException, InvocationTargetException, IllegalAccessException
     */
    void destroy() throws Exception;
}
