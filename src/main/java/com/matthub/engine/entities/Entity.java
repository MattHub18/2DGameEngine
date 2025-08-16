package com.matthub.engine.entities;

import com.matthub.engine.graphics.core.Render;
import com.matthub.engine.physics.collision.AxisAlignedBoundingBox;
import com.matthub.engine.physics.geom.Vector2D;

import java.util.EnumMap;
import java.util.Map;

public abstract class Entity {

    private int id;
    private Vector2D position;
    private Direction facing;
    private EntityState state;
    private final boolean blocksLight;

    private final float width;
    private final float height;

    protected final Map<EntityState, Sprite> animations;

    public Entity(Vector2D position, Direction facing, Sprite standSprite, boolean blocksLight) {
        this.position = position;
        this.facing = facing;
        this.state = EntityState.STAND;
        this.blocksLight = blocksLight;

        // Initialize animation map with standing sprite
        this.animations = new EnumMap<>(EntityState.class);
        this.animations.put(EntityState.STAND, standSprite);

        // Default width/height from standing sprite
        this.width = standSprite.getWidth();
        this.height = standSprite.getHeight();

        // Allow subclass to add other sprites
        initSprites();
    }
    protected abstract void initSprites();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Vector2D getPosition() { return position; }
    public void setPosition(Vector2D position) { this.position = position; }

    public Direction getFacing() { return facing; }
    public void setFacing(Direction facing) { this.facing = facing; }

    public EntityState getState() { return state; }
    public void setState(EntityState state) { this.state = state; }

    public AxisAlignedBoundingBox getBox() {
        Vector2D min = new Vector2D(position);
        Vector2D max = new Vector2D(position.x + width, position.y + height);
        return new AxisAlignedBoundingBox(min, max);
    }

    public void update(float dt) {
        Sprite sprite = animations.get(state);
        if (sprite != null)
            sprite.update(dt);
    }

    public void render(Render r) {
        Sprite sprite = animations.get(state);
        if (sprite != null) {
            sprite.render(r, position, facing, blocksLight);
        }
    }
}
