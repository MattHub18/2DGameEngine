package com.matthub.engine.world.ambient;

import com.matthub.engine.Engine;
import com.matthub.engine.entities.Entity;
import com.matthub.engine.graphics.core.Camera;
import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.world.World;
import com.matthub.engine.world.tile.Tile;
import com.matthub.engine.world.tile.TileProperties;

import java.util.ArrayList;
import java.util.List;

public abstract class Ambient {
    private Tile[][] tiles;
    private String[][] tileset;
    private int width;
    private int height;
    private List<Entity> entities;

    public Ambient() {
        this.entities = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidthInPixels() {
        return width * tiles[0][0].getTileWidth();
    }

    public int getHeightInPixels() {
        return height * tiles[0][0].getTileHeight();
    }

    public String[][] getTileset() {
        return tileset;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;

        return tiles[y][x];
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void update(Engine engine, float dt, Entity player, World world) {
        for (Entity entity : entities) {
            entity.update(dt);
        }

        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.update(dt);
            }
        }

        Tile playerTile = getHeroTile(player);
        int heroTileX = (int) playerTile.getPosition().x;
        int heroTileY = (int) playerTile.getPosition().y;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                Tile tile = getTile(heroTileX + dx, heroTileY + dy);
                if (tile == null)
                    continue;
                for(TileProperties.PropertyEntry entry : tile.getProperties().properties) {
                    if (entry.handler != null) {
                        entry.handler.apply(player, tile, world, entry.value);
                    }
                }
            }
        }

    }

    public void render(Render r, Camera camera) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.render(r, camera);
            }
        }

        for (Entity entity : entities) {
            entity.render(r, camera);
        }
    }

    public Tile getHeroTile(Entity hero) {
        int heroX = (int) (hero.getPosition().x / tiles[0][0].getTileWidth());
        int heroY = (int) (hero.getPosition().y / tiles[0][0].getTileHeight());
        return getTile(heroX, heroY);
    }
}
