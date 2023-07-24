package org.m1a2st.core.convert;

/**
 * 類型轉換抽象介面
 *
 * @Author m1a2st
 * @Date 2023/7/23
 * @Version v1.0
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
