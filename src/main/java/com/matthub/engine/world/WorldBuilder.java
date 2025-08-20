package com.matthub.engine.world;

import com.matthub.engine.entities.Entity;
import com.matthub.engine.graphics.core.Camera;
import com.matthub.engine.world.ambient.Ambient;

public class WorldBuilder {
    private final World world;

    public WorldBuilder(World world) {
        this.world = world;
    }

    public WorldBuilder setAmbient(Ambient ambient) {
        this.world.addAmbient(ambient);
        return this;
    }

    public WorldBuilder setFirstAmbient(int index){
        this.world.setCurrentAmbient(index);
        return this;
    }

    public WorldBuilder setPlayer(Entity player) {
        this.world.setPlayer(player);
        return this;
    }

    public WorldBuilder setCamera(Camera camera) {
        this.world.setCamera(camera);
        return this;
    }

    public World build() {
        return this.world;
    }
}
