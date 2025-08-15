package com.matthub.engine.graphics.gfx.wrapper;

import com.matthub.engine.graphics.gfx.Light;

public class LightWrapper {
    private final Light light;
    private final int offX;
    private final int offY;
    private final boolean movable;

    public LightWrapper(Light light, int offX, int offY, boolean movable) {
        this.light = light;
        this.offX = offX;
        this.offY = offY;
        this.movable = movable;
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

    public boolean isMovable() {
        return movable;
    }
}
