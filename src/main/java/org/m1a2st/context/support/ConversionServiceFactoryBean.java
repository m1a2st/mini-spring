package org.m1a2st.context.support;

import org.m1a2st.beans.factory.FactoryBean;
import org.m1a2st.beans.factory.InitializingBean;
import org.m1a2st.core.convert.ConversionService;
import org.m1a2st.core.convert.converter.Converter;
import org.m1a2st.core.convert.converter.ConverterFactory;
import org.m1a2st.core.convert.converter.ConverterRegistry;
import org.m1a2st.core.convert.converter.GenericConverter;
import org.m1a2st.core.convert.support.DefaultConversionService;
import org.m1a2st.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    private Set<?> converters;
    private GenericConversionService conversionService;

    @Override
    public void afterPropertiesSet() {
        conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    @Override
    public ConversionService getObject() {
        return conversionService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
