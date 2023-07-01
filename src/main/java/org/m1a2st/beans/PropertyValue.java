package org.m1a2st.beans;

/**
 * bean属性信息
 *
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}
