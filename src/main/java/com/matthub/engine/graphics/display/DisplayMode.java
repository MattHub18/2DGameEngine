package com.matthub.engine.graphics.display;

public enum DisplayMode {
    FULLSCREEN,
    WINDOWED,
    BORDERLESS;

    public static DisplayMode fromString(String mode) {
        switch (mode.toUpperCase()) {
            case "FULLSCREEN":
                return FULLSCREEN;
            case "WINDOWED":
                return WINDOWED;
            case "BORDERLESS":
                return BORDERLESS;
            default:
                throw new IllegalArgumentException("Unknown display mode: " + mode);
        }
    }
}
