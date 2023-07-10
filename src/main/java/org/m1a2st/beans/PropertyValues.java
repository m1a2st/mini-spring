package org.m1a2st.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(pv.getName())) {
                // 覆蓋原有的屬性值
                propertyValueList.set(propertyValueList.indexOf(propertyValue), pv);
                return;
            }
        }
        propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
