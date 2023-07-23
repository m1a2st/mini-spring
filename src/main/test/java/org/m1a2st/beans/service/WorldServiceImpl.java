package org.m1a2st.beans.service;

/**
 * @Author m1a2st
 * @Date 2023/7/15
 * @Version v1.0
 */
public class WorldServiceImpl implements WorldService {

    private String name;

    @Override
    public void explode() {
        System.out.println("WorldServiceImpl.explode");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
