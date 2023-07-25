package org.m1a2st.beans.bean;

import org.m1a2st.beans.factory.annotation.Value;
import org.m1a2st.stereotype.Component;

import java.time.LocalDate;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
@Component
public class Car {

    private int price;
    private LocalDate produceTime;

    @Value("${brand}")
    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(LocalDate produceTime) {
        this.produceTime = produceTime;
    }
}
