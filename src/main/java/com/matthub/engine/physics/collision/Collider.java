package com.matthub.engine.physics.collision;

import com.matthub.engine.physics.geom.Vector2D;

public interface Collider {
    Vector2D getCenter();
    boolean collision(Collider other);
    boolean collisionWithAABB(AxisAlignedBoundingBox aabb);
    boolean collisionWithRound(RoundBoundingBox round);
    Collider intersect(Collider other);
    Collider intersectWithAABB(AxisAlignedBoundingBox aabb);
    Collider intersectWithRound(RoundBoundingBox round);
}
