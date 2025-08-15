package com.matthub.engine.settings;

import com.matthub.engine.graphics.display.DisplayMode;
import com.matthub.engine.io.IO;
import com.matthub.engine.util.ResourceLoadException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WindowSettings {
    private final DisplayMode mode;
    private final int width;
    private final int height;
    private final float scale;

    public WindowSettings(String confFilename) {
        try (InputStream in = IO.loadStream(confFilename)) {
            Properties props = new Properties();
            props.load(in);
            this.mode = DisplayMode.fromString(props.getProperty("mode"));
            this.width = Integer.parseInt(props.getProperty("width"));
            this.height = Integer.parseInt(props.getProperty("height"));
            this.scale = Float.parseFloat(props.getProperty("scale"));
        } catch (IOException | NullPointerException e) {
            throw new ResourceLoadException("[DisplaySettings] Incorrect config file: " + confFilename);
        }
    }

    public DisplayMode getMode() { return mode; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getScale() { return scale; }
}
