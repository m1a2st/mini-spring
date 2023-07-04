package org.m1a2st.beans.factory;

import cn.hutool.core.bean.BeanException;

import java.util.Map;

/**
 * @Author m1a2st
 * @Date 2023/7/3
 * @Version v1.0
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 返回指定類的所有實例
     *
     * @param type 指定類別
     * @param <T>  類型
     * @return 所有此類的Bean
     * @throws BeanException 當bean不存在時
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 返回所有bean的名稱
     *
     * @return 所有bean的名稱
     */
    String[] getBeanDefinitionNames();
}
