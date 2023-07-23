package org.m1a2st.beans.factory.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author m1a2st
 * @Date 2023/7/22
 * @Version v1.0
 */
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE, ANNOTATION_TYPE})
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";
}
