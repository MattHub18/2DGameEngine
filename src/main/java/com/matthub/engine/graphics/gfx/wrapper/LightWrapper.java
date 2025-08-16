package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Light;

public class LightWrapper {
    private final Light light;
    private final int offX;
    private final int offY;

    public LightWrapper(Light light, int offX, int offY) {
        this.light = light;
        this.offX = offX;
        this.offY = offY;
    }

    public Light getLight() {
        return light;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }
}
