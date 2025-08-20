package com.matthub.engine.world.ambient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matthub.engine.entities.Entity;
import com.matthub.engine.io.IO;
import com.matthub.engine.physics.geom.Vector2D;
import com.matthub.engine.util.ResourceLoadException;
import com.matthub.engine.world.tile.Tile;
import com.matthub.engine.world.tile.TileProperties;
import com.matthub.engine.world.tile.TileRegistry;

import java.io.IOException;
import java.util.List;

public class AmbientBuilder {
    private Ambient ambient;

    public AmbientBuilder() {}

    public <T extends Ambient> AmbientBuilder initFromJson(String filename, Class<T> clazz){
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.ambient = mapper.readValue(IO.loadStream(filename), clazz);
            Tile[][] tiles = new Tile[this.ambient.getHeight()][this.ambient.getWidth()];
            String[][] tileset = this.ambient.getTileset();

            for (int y = 0; y < this.ambient.getHeight(); y++) {
                for (int x = 0; x < this.ambient.getWidth(); x++) {
                    String tileName = tileset[y][x];
                    TileProperties properties = TileRegistry.get(tileName);
                    tiles[y][x] = new Tile(new Vector2D(x,y) ,properties);
                }
            }
            this.ambient.setTiles(tiles);
            return this;
        }catch (IOException e){
            throw new ResourceLoadException("Error loading ambient from JSON file: " + filename);
        }
    }

    public AmbientBuilder setEntities(List<Entity> entities){
        this.ambient.setEntities(entities);
        return this;
    }

    public AmbientBuilder addEntity(Entity entity) {
        this.ambient.addEntity(entity);
        return this;
    }

    public Ambient build() {
        return this.ambient;
    }
}
