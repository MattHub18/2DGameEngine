package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Rectangle;
import com.matthub.engine.graphics.renders.RectangleRender;

public class BorderRectangleWrapper extends RectangleWrapper{
    private final int thickness;

    public BorderRectangleWrapper(Rectangle rectangle, int offX, int offY, int thickness, boolean movable, int color) {
        super(rectangle, offX, offY, movable, color);
        this.thickness = thickness;
    }

    public int getThickness() {
        return thickness;
    }

    @Override
    public void renderWith(RectangleRender render){
        render.drawBorderRectangle(this);
    }
}
