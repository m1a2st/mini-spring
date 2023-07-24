package org.m1a2st.core.convert.support;

import org.m1a2st.core.convert.converter.ConverterRegistry;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class DefaultConversionService extends GenericConversionService {

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
        //TODO 添加其他ConverterFactory
    }
}
