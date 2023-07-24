package org.m1a2st.core.convert.converter;

/**
 * 類型轉換工廠
 *
 * @Author m1a2st
 * @Date 2023/7/23
 * @Version v1.0
 */
public interface ConverterFactory<S, R> {

    /**
     * 獲取轉換器
     *
     * @param targetType 目標類型
     * @param <T>        目標類型
     * @return 轉換器
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
