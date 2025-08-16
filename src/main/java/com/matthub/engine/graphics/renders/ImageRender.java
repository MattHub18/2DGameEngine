package com.matthub.engine.graphics.renders;

import com.matthub.engine.graphics.gfx.Image;
import com.matthub.engine.graphics.gfx.wrapper.ImageWrapper;

import java.util.ArrayList;

public class ImageRender implements RenderInterface {

    private final ArrayList<ImageWrapper> images;
    private final PixelRender render;

    public ImageRender(PixelRender render) {
        this.render = render;
        this.images = new ArrayList<>();
    }

    @Override
    public void process() {
        for (ImageWrapper img : this.images) {
            drawImage(img);
        }
    }

    @Override
    public void clear() {
        this.images.clear();
    }

    public void addImage(Image image, int offX, int offY, boolean transparent) {
        this.images.add(new ImageWrapper(image, offX, offY, transparent));
    }

    private void drawImage(ImageWrapper wrapper) {
        Image image = wrapper.getImage();
        int offX = wrapper.getOffX();
        int offY = wrapper.getOffY();
        boolean transparent = wrapper.isTransparent();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                this.render.setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()]);
                this.render.setBrightness(x + offX, y + offY, transparent);
            }
        }
    }
}

