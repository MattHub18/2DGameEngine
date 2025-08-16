package com.matthub.engine.graphics.renders;

import com.matthub.engine.graphics.gfx.Rectangle;
import com.matthub.engine.graphics.gfx.wrapper.BorderRectangleWrapper;
import com.matthub.engine.graphics.gfx.wrapper.RectangleWrapper;

import java.util.ArrayList;
import java.util.List;

public class RectangleRender implements RenderInterface{
    private final List<RectangleWrapper> rectangles;
    private final PixelRender render;

    public RectangleRender(PixelRender render) {
        this.render = render;
        this.rectangles = new ArrayList<>();
    }

    @Override
    public void process() {
        for (RectangleWrapper rect : this.rectangles) {
            rect.renderWith(this);
        }
    }

    @Override
    public void clear() {
        this.rectangles.clear();
    }

    public void addRectangle(int offX, int offY, int width, int height, int color) {
        this.rectangles.add(new RectangleWrapper(new Rectangle(width, height), offX, offY, color));
    }

    public void addBorderRectangle(int offX, int offY, int width, int height, int thickness, int color) {
        this.rectangles.add(new BorderRectangleWrapper(new Rectangle(width, height), offX, offY, thickness, color));
    }

    private void drawEdges(int x0, int y0, int w, int h, int color) {
        if (w <= 0 || h <= 0) return;

        // vertical edges
        for (int y = 0; y < h; y++) {
            render.setPixel(x0, y0 + y, color);
            render.setPixel(x0 + w - 1, y0 + y, color);
        }

        // horizontal edges
        for (int x = 1; x < w - 1; x++) {
            render.setPixel(x0 + x, y0, color);
            render.setPixel(x0 + x, y0 + h - 1, color);
        }
    }

    public void drawRectangle(RectangleWrapper wrapper) {
        int offX = wrapper.getOffX();
        int offY = wrapper.getOffY();
        int width = wrapper.getRectangle().getWidth();
        int height = wrapper.getRectangle().getHeight();
        int color = wrapper.getColor();

        drawEdges(offX, offY, width, height, color);
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                render.setPixel(offX + x, offY + y, color);
            }
        }
    }

    public void drawBorderRectangle(BorderRectangleWrapper wrapper) {
        int offX = wrapper.getOffX();
        int offY = wrapper.getOffY();
        int width = wrapper.getRectangle().getWidth();
        int height = wrapper.getRectangle().getHeight();
        int color = wrapper.getColor();
        int thickness = wrapper.getThickness();

        for (int t = 0; t < thickness; t++) {
            drawEdges(offX + t, offY + t, width - t * 2, height - t * 2, color);
        }
    }
}
