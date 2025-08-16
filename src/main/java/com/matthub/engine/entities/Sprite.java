package com.matthub.engine.entities;

import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.graphics.gfx.TileImage;
import com.matthub.engine.physics.geom.Vector2D;

import java.util.EnumMap;
import java.util.Map;

public class Sprite {
    private final TileImage texture;
    private final Map<Direction, Integer> directionRowMap;
    private final int maxFrames;
    private final float animationSpeed; // frames per second
    private float currentFrame;

    private final int width;
    private final int height;

    public Sprite(TileImage texture, Map<Direction, Integer> directionRowMap, int maxFrames, float animationSpeed) {
        this.texture = texture;
        this.maxFrames = maxFrames;
        this.animationSpeed = animationSpeed;
        this.currentFrame = 0;

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        Map<Direction, Integer> map = new EnumMap<>(Direction.class);
        if (directionRowMap != null) {
            map.putAll(directionRowMap);
        }
        int defaultRow = 0;
        for (Direction dir : Direction.values()) {
            map.putIfAbsent(dir, defaultRow++);
        }
        this.directionRowMap = Map.copyOf(map);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void update(float dt) {
        currentFrame = (currentFrame + dt * animationSpeed) % maxFrames;
    }

    public void render(Render r, Vector2D position, Direction facing, boolean blocksLight) {
        int rowIndex = directionRowMap.get(facing);
        r.addImage(texture.getTile(rowIndex, (int) currentFrame), (int) position.x, (int) position.y, blocksLight);
    }

    public boolean isAnimationEnd() {
        return currentFrame >= maxFrames - 1;
    }

    // Helper for creating a row mapping from sheet order
    public static Map<Direction, Integer> mapFromList(Direction... sheetOrder) {
        Map<Direction, Integer> map = new EnumMap<>(Direction.class);
        for (int i = 0; i < sheetOrder.length; i++) {
            map.put(sheetOrder[i], i);
        }
        return map;
    }
}
