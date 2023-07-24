package org.m1a2st.core.convert.support;

import org.m1a2st.core.convert.ConversionService;
import org.m1a2st.core.convert.converter.Converter;
import org.m1a2st.core.convert.converter.ConverterFactory;
import org.m1a2st.core.convert.converter.ConverterRegistry;
import org.m1a2st.core.convert.converter.GenericConverter;
import org.m1a2st.core.convert.converter.GenericConverter.ConvertiblePair;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static java.util.Collections.singleton;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

    private final Map<ConvertiblePair, GenericConverter> converters = new HashMap<>();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = getConverter(sourceType, targetType);
        return converter != null;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        GenericConverter converter = getConverter(sourceType, targetType);
        return (T) converter.convert(source, sourceType, targetType);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
        for (ConvertiblePair convertibleType : converterAdapter.getConvertibleTypes()) {
            converters.put(convertibleType, converterAdapter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        ConvertiblePair typeInfo = getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInfo, converterFactory);
        for (ConvertiblePair convertibleType : converterFactoryAdapter.getConvertibleTypes()) {
            converters.put(convertibleType, converterFactoryAdapter);
        }
    }

    @Override
    public void addConverter(GenericConverter converter) {
        for (ConvertiblePair convertibleType : converter.getConvertibleTypes()) {
            converters.put(convertibleType, converter);
        }
    }

    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sourceCandidate : sourceCandidates) {
            for (Class<?> targetCandidate : targetCandidates) {
                ConvertiblePair convertiblePair = new ConvertiblePair(sourceCandidate, targetCandidate);
                GenericConverter converter = converters.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        ArrayList<Class<?>> hierarchy = new ArrayList<>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    private ConvertiblePair getRequiredTypeInfo(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class<?> sourceType = (Class<?>) actualTypeArguments[0];
        Class<?> targetType = (Class<?>) actualTypeArguments[1];
        return new ConvertiblePair(sourceType, targetType);
    }

    private static final class ConverterAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;
        private final Converter<Object, Object> converter;

        public ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converter.convert(source);
        }
    }

    private static final class ConverterFactoryAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;
        private final ConverterFactory<Object, Object> converterFactory;

        public ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class<?> sourceType, Class<?> targetType) {
            return converterFactory.getConverter(targetType).convert(source);
        }
    }
}
