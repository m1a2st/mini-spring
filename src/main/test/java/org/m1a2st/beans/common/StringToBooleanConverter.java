package org.m1a2st.beans.common;

import org.m1a2st.core.convert.converter.GenericConverter;

import java.util.Set;

import static java.util.Collections.singleton;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class StringToBooleanConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return singleton(new ConvertiblePair(String.class, Boolean.class));
    }

    @Override
    public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
        return Boolean.valueOf((String) source);
    }
}
