package com.matthub.engine.util;

public class ResourceLoadException extends RuntimeException {
    public ResourceLoadException(String message) {
        super(message);
    }

    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
