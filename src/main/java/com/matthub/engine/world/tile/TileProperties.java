package com.matthub.engine.world.tile;

import com.matthub.engine.entities.Direction;
import com.matthub.engine.world.tile.tileresponse.TileResponse;

import java.util.ArrayList;
import java.util.List;

public class TileProperties {
    public String spritePath;
    public int tileWidth;
    public int tileHeight;
    public Direction direction;
    public int maxFrames;
    public float animationSpeed;
    public boolean blocksLight;

    public List<PropertyEntry> properties;

    public TileProperties(){
        this.properties = new ArrayList<>();
    }

    public TileProperties setSpriteProperties(String spritePath, int tileWidth, int tileHeight, Direction direction, int maxFrames, float animationSpeed, boolean blocksLight) {
        this.spritePath = spritePath;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.direction = direction;
        this.maxFrames = maxFrames;
        this.animationSpeed = animationSpeed;
        this.blocksLight = blocksLight;
        return this;
    }

    public TileProperties setProperty(TileResponse handler, Object value) {
        this.properties.add(new PropertyEntry(handler, value));
        return this;
    }

    public static class PropertyEntry {
        public TileResponse handler;
        public Object value;

        public PropertyEntry(TileResponse handler, Object value) {
            this.handler = handler;
            this.value = value;
        }
    }
}
