package org.m1a2st.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }
}
