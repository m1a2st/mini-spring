package org.m1a2st.beans.common;

import org.m1a2st.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/24
 * @Version v1.0
 */
public class ConverterFactoryBean implements FactoryBean<Set<?>> {

    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter converter = new StringToLocalDateConverter("yyy/MM/dd");
        converters.add(converter);
        return converters;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
