package com.matthub.engine.world.tile.tileresponse;

import com.matthub.engine.entities.Entity;
import com.matthub.engine.world.World;
import com.matthub.engine.world.tile.Tile;

public interface TileResponse {
    void apply(Entity player, Tile tile, World world, Object value);
}
