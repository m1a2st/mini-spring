package org.m1a2st.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public class DefaultResourceLoader implements ResourceLoader {

    public static final String CLASSPATH_URL_PREFIX = "classpath:";


    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            // 嘗試當成Url處理
            try {
                return new UrlResource(new URL(location));
            } catch (MalformedURLException ex) {
                // 當成文件系統下的資源處理
                return new FileSystemResource(location);
            }
        }
    }
}
