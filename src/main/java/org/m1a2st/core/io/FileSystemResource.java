package org.m1a2st.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * @Author m1a2st
 * @Date 2023/7/2
 * @Version v1.0
 */
public class FileSystemResource implements Resource {

    private final String filePath;

    public FileSystemResource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path path = new File(filePath).toPath();
            return Files.newInputStream(path);
        } catch (NoSuchFileException e) {
            throw new NoSuchFileException(e.getMessage());
        }
    }
}
