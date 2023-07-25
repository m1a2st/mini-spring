package org.m1a2st.beans.common;

import org.m1a2st.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter DATE_TIME_FORMATTER;

    public StringToLocalDateConverter(String s) {
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(s);
    }

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DATE_TIME_FORMATTER);
    }
}
