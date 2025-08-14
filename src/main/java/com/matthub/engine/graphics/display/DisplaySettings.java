package com.matthub.engine.graphics.display;

public class DisplaySettings {
    private DisplayMode mode;
    private int width, height;
    private float scale;

    public DisplaySettings(DisplayMode mode, int width, int height, float scale) {
        this.mode = mode;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public DisplayMode getMode() { return mode; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getScale() { return scale; }
}
