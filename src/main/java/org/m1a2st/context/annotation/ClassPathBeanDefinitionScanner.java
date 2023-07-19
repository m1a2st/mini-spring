package org.m1a2st.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.m1a2st.beans.factory.config.BeanDefinition;
import org.m1a2st.beans.factory.support.BeanDefinitionRegistry;
import org.m1a2st.stereotype.Component;

import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/19
 * @Version v1.0
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                // 解析Bean的作用域
                String beanScope = resolveScope(candidate);
                // 將Bean註冊到BeanDefinition中
                if (StrUtil.isNotEmpty(beanScope)) {
                    candidate.setScope(beanScope);
                }
                // 生成Bean的名稱
                String beanName = determineBeanName(candidate);
                // 註冊BeanDefinition
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
    }

    private String resolveScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            return StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
