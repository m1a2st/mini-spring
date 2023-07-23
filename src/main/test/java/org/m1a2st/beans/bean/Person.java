package org.m1a2st.beans.bean;

import org.m1a2st.beans.BeansException;
import org.m1a2st.beans.factory.DisposableBean;
import org.m1a2st.beans.factory.InitializingBean;
import org.m1a2st.beans.factory.annotation.Autowired;
import org.m1a2st.stereotype.Component;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
@Component
public class Person implements InitializingBean, DisposableBean {

    private String name;
    private int age;
    @Autowired
    private Car car;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void customInitMethod() {
        System.out.println("I was born in the method named customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("InitializingBean#afterPropertiesSet");
    }
}
