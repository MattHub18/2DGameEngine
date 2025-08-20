package com.matthub.engine.graphics.core;

import com.matthub.engine.entities.Entity;
import com.matthub.engine.world.ambient.Ambient;

public class Camera {
    private int camX;
    private int camY;

    public void update(Entity player, Ambient ambient, int viewportWidth, int viewportHeight) {
        // Center camera
        camX = (int) (player.getPosition().x - (float) viewportWidth / 2);
        camY = (int) (player.getPosition().y - (float) viewportHeight / 2);

        // Clamp to world
        camX = Math.max(0, Math.min(camX, ambient.getWidthInPixels() - viewportWidth));
        camY = Math.max(0, Math.min(camY, ambient.getHeightInPixels() - viewportHeight));
    }

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }
}
