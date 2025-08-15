package com.matthub.engine.graphics.renders;

import com.matthub.engine.graphics.gfx.Light;
import com.matthub.engine.graphics.gfx.wrapper.LightWrapper;

import java.util.ArrayList;

public class LightRender implements RenderInterface {
    private final ArrayList<LightWrapper> lights;
    private final PixelRender render;

    public LightRender(PixelRender render) {
        this.render = render;
        this.lights = new ArrayList<>();
    }

    @Override
    public void process() {
        for (LightWrapper light : this.lights) {
            drawLight(light.getLight(), light.getOffX(), light.getOffY(), light.isMovable());
        }
    }

    @Override
    public void clear() {
        this.lights.clear();
    }

    public void addLight(Light light, int offX, int offY, boolean movable) {
        this.lights.add(new LightWrapper(light, offX, offY, movable));
    }

    private void drawLight(Light light, int offX, int offY, boolean movable) {
        for (int i = 0; i <= light.getDiameter(); i++) {
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY, movable);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offX, offY, movable);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY, movable);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offX, offY, movable);
        }
    }

    private void drawLightLine(Light light, int x0, int y0, int x1, int y1, int offX, int offY, boolean movable) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int errD = dx - dy;
        int err;

        while (true) {

            int screenX = x0 + offX;
            int screenY = y0 + offY;

            int lightColor = light.getLightValue(x0, y0);

            if (lightColor == 0)
                return;

            if (!this.render.getBrightness(screenX, screenY))
                return;

            setLightPixels(screenX, screenY, lightColor, movable);

            if (x0 == x1 && y0 == y1)
                break;

            err = 2 * errD;
            if (err > -1 * dy) {
                errD -= dy;
                x0 += sx;
            }
            if (err < dx) {
                errD += dx;
                y0 += sy;
            }
        }
    }

    private void setLightPixels(int x, int y, int value, boolean movable) {
        int baseColor = render.getLightPixel(x, y);
        int maxRed = Math.max(((baseColor >> 16) & 0xff), ((value >> 16) & 0xff));
        int maxGreen = Math.max(((baseColor >> 8) & 0xff), ((value >> 8) & 0xff));
        int maxBlue = Math.max(((baseColor) & 0xff), ((value) & 0xff));
        this.render.setLightPixel(x, y, (maxRed << 16 | maxGreen << 8 | maxBlue), movable);
    }
}
