package com.matthub.engine.world;

import com.matthub.engine.Engine;
import com.matthub.engine.entities.Entity;
import com.matthub.engine.graphics.core.Camera;
import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.world.ambient.Ambient;

import java.util.ArrayList;
import java.util.List;

public abstract class World {
    private final List<Ambient> ambients;
    private Ambient currentAmbient;
    private Entity player;
    private Camera camera;

    public World() {
        this.ambients = new ArrayList<>();
    }

    public void addAmbient(Ambient ambient) {
        this.ambients.add(ambient);
    }

    public void setCurrentAmbient(int index) {
        this.currentAmbient = this.ambients.get(index);
    }

    public Ambient getCurrentAmbient() {
        return this.currentAmbient;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void update(Engine engine, float dt, int viewportWidth, int viewportHeight){
        this.currentAmbient.update(engine, dt, player, this);
        this.player.update(dt);
        this.camera.update(player, this.currentAmbient, viewportWidth, viewportHeight);
    }

    public void render(Render r){
        this.currentAmbient.render(r, camera);
        this.player.render(r, camera);
    }
}
