package org.m1a2st.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException(path + " cannot be opened because it does not exist");
        }
        return inputStream;
    }
}
