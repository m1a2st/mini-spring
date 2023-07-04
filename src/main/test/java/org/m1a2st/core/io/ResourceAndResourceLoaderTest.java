package org.m1a2st.core.io;

import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public class ResourceAndResourceLoaderTest {

    @Test
    public void testResourceLoader() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        // 測試加載classpath下的資源
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        assertEquals("hello world", content);

        // 測試加載文件系統資源
        resource = resourceLoader.getResource("src/main/test/resources/hello.txt");
        assertTrue(resource instanceof FileSystemResource);
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        assertEquals("hello world", content);

        // 測試加載url資源
        resource = resourceLoader.getResource("https://www.google.com");
        assertTrue(resource instanceof UrlResource);
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        assertTrue(content.contains("google"));
    }
}
