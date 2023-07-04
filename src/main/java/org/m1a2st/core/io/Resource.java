package org.m1a2st.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 資源的抽象和訪問介面
 *
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
