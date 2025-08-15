package com.matthub.engine.graphics.core;

import com.matthub.engine.graphics.gfx.Image;
import com.matthub.engine.graphics.gfx.Light;
import com.matthub.engine.graphics.gfx.Text;
import com.matthub.engine.graphics.renders.*;
import com.matthub.engine.settings.GraphicSettings;

public class Render {
    private final PixelRender render;
    private final RectangleRender rectangleRender;
    private final LightRender lightRender;
    private final ImageRender imageRender;
    private final TextRender textRender;

    public Render(Window window, GraphicSettings settings) {
        this.render = new PixelRender(window, settings);
        this.rectangleRender = new RectangleRender(this.render);
        this.lightRender = new LightRender(this.render);
        this.imageRender = new ImageRender(this.render);
        this.textRender = new TextRender(this.render);
    }

    public void process() {
        this.rectangleRender.process();
        this.imageRender.process();
        this.lightRender.process();
        this.textRender.process();
        this.render.process();
    }

    public void clear() {
        this.render.clear();
        this.rectangleRender.clear();
        this.lightRender.clear();
        this.imageRender.clear();
        this.textRender.clear();
    }

    public void addRectangle(int offX, int offY, int width, int height, boolean movable, int color) {
        this.rectangleRender.addRectangle(offX, offY, width, height, movable, color);
    }

    public void addBorderRectangle(int offX, int offY, int width, int height, int thickness, boolean movable, int color) {
        this.rectangleRender.addBorderRectangle(offX, offY, width, height, thickness, movable, color);
    }

    public void addLight(Light light, int offX, int offY, boolean movable) {
        this.lightRender.addLight(light, offX, offY, movable);
    }

    public void addImage(Image image, int offX, int offY, boolean movable, boolean transparent) {
        this.imageRender.addImage(image, offX, offY, movable, transparent);
    }

    public void addText(Text text, int offX, int offY, boolean movable) {
        this.textRender.addText(text, offX, offY, movable);
    }
}
