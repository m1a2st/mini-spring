package org.m1a2st.core.convert.converter;

/**
 * 類型轉換器註冊介面
 *
 * @Author m1a2st
 * @Date 2023/7/23
 * @Version v1.0
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);
}
