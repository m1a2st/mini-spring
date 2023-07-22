package org.m1a2st.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author m1a2st
 * @Date 2023/7/20
 * @Version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER})
public @interface Value {

    String value();
}
