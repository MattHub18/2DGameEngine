package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Rectangle;
import com.matthub.engine.graphics.renders.RectangleRender;

public class RectangleWrapper {
    private final Rectangle rectangle;
    private final int offX;
    private final int offY;
    private final boolean movable;
    private final int color;

    public RectangleWrapper(Rectangle rectangle, int offX, int offY, boolean movable, int color) {
        this.rectangle = rectangle;
        this.offX = offX;
        this.offY = offY;
        this.movable = movable;
        this.color = color;
    }

    public Rectangle getRectangle() {
        return rectangle;
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

    public int getColor() {
        return color;
    }

    public void renderWith(RectangleRender render){
        render.drawRectangle(this);
    }
}
