package org.m1a2st.beans;

/**
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
