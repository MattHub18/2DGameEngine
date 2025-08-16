package com.matthub.engine.graphics.renders;

import com.matthub.engine.graphics.core.Window;
import com.matthub.engine.settings.GraphicSettings;

import java.awt.image.DataBufferInt;

public class PixelRender implements RenderInterface{
    private final Window window;
    private final int[] pixels;
    private final int[] lightPixels;
    private final boolean[] brightness;
    private final int bright;

    public PixelRender(Window window, GraphicSettings settings) {
        this.window = window;
        this.pixels = ((DataBufferInt) this.window.getImage().getRaster().getDataBuffer()).getData();
        this.lightPixels = new int[pixels.length];
        this.brightness = new boolean[pixels.length];
        this.bright = 0x00111111 * (15 * settings.getBrightPercentage() / 100);
    }

    @Override
    public void process() {
        for (int i = 0; i < pixels.length; i++) {
            float red = ((lightPixels[i] >> 16) & 0xff) / 255f;
            float green = ((lightPixels[i] >> 8) & 0xff) / 255f;
            float blue = ((lightPixels[i]) & 0xff) / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 | (int) (((pixels[i] >> 8) & 0xff) * green) << 8 | (int) (((pixels[i]) & 0xff) * blue));
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
            lightPixels[i] = bright;
            brightness[i] = false;
        }
    }

    public void setPixel(int x, int y, int value) {
        if (isOffScreen(x, y))
            return;

        int alpha = (value >> 24) & 0xff;

        if (alpha == 0)
            return;

        int index = x + y * window.getWidth();

        if (alpha == 255)
            pixels[index] = value;
        else {
            int pixelColor = pixels[index];
            int red = ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int green = ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int blue = ((pixelColor) & 0xff) - (int) ((((pixelColor) & 0xff) - ((value) & 0xff)) * (alpha / 255f));
            pixels[index] = (red << 16 | green << 8 | blue);
        }
    }

    public void setBrightness(int x, int y, boolean value) {
        if (isOffScreen(x, y))
            return;
        brightness[x+y* window.getWidth()] = value;
    }

    public boolean getBrightness(int x, int y) {
        if (isOffScreen(x, y))
            return false;
        return brightness[x + y * window.getWidth()];
    }

    public int getLightPixel(int x, int y) {
        if (isOffScreen(x, y))
            return 0;
        return lightPixels[x + y * window.getWidth()];
    }

    public void setLightPixel(int x, int y, int value) {
        if (isOffScreen(x, y))
            return;
        lightPixels[x + y * window.getWidth()] = value;
    }

    private boolean isOffScreen(int x, int y) {
        int cx = 0;
        int cy = 0;
        int vx = window.getWidth();
        int vy = window.getHeight();

        return x < cx || x >= vx || y < cy || y >= vy;
    }
}
