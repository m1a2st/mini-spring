package org.m1a2st.beans.bean;

/**
 * @Author m1a2st
 * @Date 2023/7/1
 * @Version v1.0
 */
public class Person {

    private String name;
    private int age;
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
}
