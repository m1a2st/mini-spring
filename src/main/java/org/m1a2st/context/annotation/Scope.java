package org.m1a2st.context.annotation;

import java.lang.annotation.*;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
