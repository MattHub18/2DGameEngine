package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Image;

public class ImageWrapper {
    private final Image image;
    private final int offX;
    private final int offY;
    private final boolean movable;
    private final boolean transparent;

    public ImageWrapper(Image image, int offX, int offY, boolean movable, boolean transparent) {
        this.image = image;
        this.offX = offX;
        this.offY = offY;
        this.movable = movable;
        this.transparent = transparent;
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

    public boolean isMovable() {
        return movable;
    }

    public boolean isTransparent() {
        return transparent;
    }
}

