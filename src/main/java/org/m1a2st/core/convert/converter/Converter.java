package org.m1a2st.core.convert.converter;

/**
 * 類型轉換抽象介面
 *
 * @Author m1a2st
 * @Date 2023/7/23
 * @Version v1.0
 */
public interface Converter<S, T> {
    /**
     * 類型轉換
     *
     * @param source 源類型
     * @return 轉換後的類型
     */
    T convert(S source);
}
