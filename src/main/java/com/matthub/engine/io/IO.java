package com.matthub.engine.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class IO {
    public static InputStream loadStream(String path) throws IOException {
        InputStream in = IO.class.getClassLoader().getResourceAsStream(path);
        if (in != null) return in;

        File file = new File(path);
        if (file.exists()) return Files.newInputStream(file.toPath());

        throw new IOException("Resource not found: " + path);
    }
}
