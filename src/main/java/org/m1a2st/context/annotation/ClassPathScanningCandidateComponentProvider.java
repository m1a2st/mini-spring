package org.m1a2st.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        LinkedHashSet<BeanDefinition> candidates = new LinkedHashSet<>();
        // 掃描有org.m1a2st.stereotype.Component註解的類
        ClassUtil.scanPackageByAnnotation(basePackage, Component.class)
                .forEach(clazz -> {
                    candidates.add(new BeanDefinition(clazz));
                });
        return candidates;
    }
}
