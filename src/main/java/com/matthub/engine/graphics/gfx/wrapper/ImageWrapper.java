package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Image;

public class ImageWrapper {
    private final Image image;
    private final int offX;
    private final int offY;
    private final boolean blocksLight;

    public ImageWrapper(Image image, int offX, int offY, boolean blocksLight) {
        this.image = image;
        this.offX = offX;
        this.offY = offY;
        this.blocksLight = blocksLight;
    }

    public Image getImage() {
        return image;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }

    public boolean doesBlockLight() {
        return blocksLight;
    }
}

