package com.matthub.engine.graphics.display;

import com.matthub.engine.io.filesystem.Archive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DisplaySettings {
    private final DisplayMode mode;
    private final int width;
    private final int height;
    private final float scale;

    public DisplaySettings(String confFilename) {
        try (InputStream in = Archive.class.getClassLoader().getResourceAsStream(confFilename)) {
            if (in == null) {
                throw new IllegalStateException("[DisplaySettings] Missing config file: " + confFilename);
            }
            Properties props = new Properties();
            props.load(in);
            this.mode = DisplayMode.fromString(props.getProperty("mode"));
            this.width = Integer.parseInt(props.getProperty("width"));
            this.height = Integer.parseInt(props.getProperty("height"));
            this.scale = Float.parseFloat(props.getProperty("scale"));
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("[DisplaySettings] Incorrect config file: " + confFilename);
        }
    }

    public DisplayMode getMode() { return mode; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getScale() { return scale; }
}
