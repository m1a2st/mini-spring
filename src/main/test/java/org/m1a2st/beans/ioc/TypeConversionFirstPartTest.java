package org.m1a2st.beans.ioc;

import org.junit.jupiter.api.Test;
import org.m1a2st.beans.common.StringToBooleanConverter;
import org.m1a2st.beans.common.StringToIntegerConverter;
import org.m1a2st.core.convert.converter.Converter;
import org.m1a2st.core.convert.support.GenericConversionService;
import org.m1a2st.core.convert.support.StringToNumberConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class TypeConversionFirstPartTest {

    @Test
    public void testStringToIntegerConverter() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("8888");
        assertThat(num).isEqualTo(8888);
    }

    @Test
    public void testStringToNumberConverterFactory() {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        Integer intNum = stringToIntegerConverter.convert("8888");
        assertThat(intNum).isEqualTo(8888);

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        Long longNum = stringToLongConverter.convert("8888");
        assertThat(longNum).isEqualTo(8888L);
    }

    @Test
    public void testGenericConverter() {
        StringToBooleanConverter converter = new StringToBooleanConverter();

        Boolean flag = (Boolean) converter.convert("true", String.class, Boolean.class);
        assertThat(flag).isTrue();
    }

    @Test
    public void testGenericConversionService() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new StringToIntegerConverter());

        Integer intNum = conversionService.convert("8888", Integer.class);
        assertThat(conversionService.canConvert(String.class, Integer.class)).isTrue();
        assertThat(intNum).isEqualTo(8888);

        conversionService.addConverterFactory(new StringToNumberConverterFactory());
        assertThat(conversionService.canConvert(String.class, Long.class)).isTrue();
        Long longNum = conversionService.convert("8888", Long.class);
        assertThat(longNum).isEqualTo(8888L);

        conversionService.addConverter(new StringToBooleanConverter());
        assertThat(conversionService.canConvert(String.class, Boolean.class)).isTrue();
        Boolean flag = conversionService.convert("true", Boolean.class);
        assertThat(flag).isTrue();
    }
}
