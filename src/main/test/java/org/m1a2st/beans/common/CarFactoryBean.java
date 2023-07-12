package org.m1a2st.beans.common;

import org.m1a2st.beans.bean.Car;
import org.m1a2st.beans.factory.FactoryBean;

/**
 * @Author m1a2st
 * @Date 2023/7/12
 * @Version v1.0
 */
public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
