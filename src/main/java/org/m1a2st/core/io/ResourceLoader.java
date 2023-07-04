package org.m1a2st.core.io;

/**
 * 資源類加載器介面
 *
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public interface ResourceLoader {

    Resource getResource(String location);
}
