package com.matthub.engine.world.tile;

import com.matthub.engine.entities.Sprite;
import com.matthub.engine.graphics.core.Camera;
import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.graphics.gfx.TileImage;
import com.matthub.engine.io.filesystem.Archive;
import com.matthub.engine.io.filesystem.ResourceType;
import com.matthub.engine.physics.geom.Vector2D;

public class Tile {
    private final Vector2D position;
    private final TileProperties properties;
    private final int tileWidth;
    private final int tileHeight;
    private final Sprite sprite;

    public Tile(Vector2D position, TileProperties properties) {
        this.position = position;
        this.properties = properties;
        this.tileWidth = properties.tileWidth;
        this.tileHeight = properties.tileHeight;
        this.sprite = new Sprite(new TileImage(Archive.get(ResourceType.TEXTURE,this.properties.spritePath),
                this.tileWidth, this.tileHeight),Sprite.mapFromList(this.properties.direction),this.properties.maxFrames, this.properties.animationSpeed);
    }

    public Vector2D getPosition(){
        return position;
    }

    public TileProperties getProperties() {
        return properties;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void update(float dt) {
        sprite.update(dt);
    }

    public void render(Render r, Camera camera) {
        // Calculate the render position based on camera offset
        Vector2D renderPosition = new Vector2D(position.x*this.tileWidth - camera.getCamX(), position.y*this.tileHeight - camera.getCamY());
        // Render the sprite at the calculated position
        sprite.render(r, renderPosition, this.properties.direction, this.properties.blocksLight);
    }
}
