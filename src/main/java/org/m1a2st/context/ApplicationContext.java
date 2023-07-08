package org.m1a2st.context;

import org.m1a2st.beans.factory.HierarchicalBeanFactory;
import org.m1a2st.beans.factory.ListableBeanFactory;
import org.m1a2st.core.io.ResourceLoader;

/**
 * @Author m1a2st
 * @Date 2023/7/6
 * @Version v1.0
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
