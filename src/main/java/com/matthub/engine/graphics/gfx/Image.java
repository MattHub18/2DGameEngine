package com.matthub.engine.graphics.gfx;

import com.matthub.engine.io.IO;
import com.matthub.engine.util.ResourceLoadException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {
    private final int width;
    private final int height;
    private final int[] pixels;

    public Image(String path) {
        try {
            BufferedImage image = ImageIO.read(IO.loadStream(path));
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
            image.flush();
        } catch (IOException e) {
            throw new ResourceLoadException("[Image] Could not load internal resource: " + path);
        }
    }

    public Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

