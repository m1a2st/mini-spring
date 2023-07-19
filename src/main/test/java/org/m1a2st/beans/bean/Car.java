package org.m1a2st.beans.bean;

import org.m1a2st.stereotype.Component;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
@Component
public class Car {

    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
