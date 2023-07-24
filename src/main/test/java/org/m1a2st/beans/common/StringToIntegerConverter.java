package org.m1a2st.beans.common;

import org.m1a2st.core.convert.converter.Converter;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
