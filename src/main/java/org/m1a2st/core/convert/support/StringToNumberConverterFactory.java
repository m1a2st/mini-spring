package org.m1a2st.core.convert.support;

import org.m1a2st.core.convert.converter.Converter;
import org.m1a2st.core.convert.converter.ConverterFactory;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (source.length() == 0) {
                return null;
            }

            if (targetType.equals(Integer.class)) {
                return (T) Integer.valueOf(source);
            } else if (targetType.equals(Long.class)) {
                return (T) Long.valueOf(source);
            }
            //TODO 其他數字類型

            else {
                throw new IllegalArgumentException(
                        "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]");
            }
        }
    }
}
