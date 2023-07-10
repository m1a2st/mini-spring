package org.m1a2st.context.support;

import org.m1a2st.beans.BeansException;

/**
 * @Author m1a2st
 * @Date 2023/7/7
 * @Version v1.0
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;

    /**
     * 從xml文件加載BeanDefinition，並且自動刷新上下文
     *
     * @param configLocation xml配置文件
     * @throws BeansException 應用上下文創建失敗
     */
    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    /**
     * 從xml文件加載BeanDefinition，並且自動刷新上下文
     *
     * @param configLocations xml配置文件
     * @throws BeansException 應用上下文創建失敗
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
