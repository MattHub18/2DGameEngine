package com.matthub.engine.physics.collision;


import com.matthub.engine.physics.geom.Vector2D;

public class RoundBoundingBox implements Collider {

    private Vector2D center;
    private final float radius;

    public RoundBoundingBox(Vector2D center, float radius) {
        this.center = new Vector2D(center);
        this.radius = radius;
    }

    public Vector2D getCenter() {
        return new Vector2D(center);
    }

    public float getRadius() {
        return radius;
    }

    public void setCenter(Vector2D newCenter) {
        this.center = new Vector2D(newCenter);
    }

    @Override
    public boolean collision(Collider other) {
        return other.collisionWithRound(this);
    }

    @Override
    public boolean collisionWithAABB(AxisAlignedBoundingBox aabb) {
        Vector2D min = aabb.getMin();
        Vector2D max = aabb.getMax();

        float closestX = Math.max(min.x, Math.min(center.x, max.x));
        float closestY = Math.max(min.y, Math.min(center.y, max.y));
        Vector2D closestPoint = new Vector2D(closestX, closestY);

        Vector2D delta = center.subtract(closestPoint);
        return delta.lengthSquared() <= radius * radius;
    }

    @Override
    public boolean collisionWithRound(RoundBoundingBox other) {
        Vector2D delta = center.subtract(other.center);
        float radiusSum = this.radius + other.radius;
        return delta.lengthSquared() <= radiusSum * radiusSum;
    }

    @Override
    public Collider intersect(Collider other) {
        return other.intersectWithRound(this);
    }

    @Override
    public Collider intersectWithAABB(AxisAlignedBoundingBox aabb) {
        return null; // optional
    }

    @Override
    public Collider intersectWithRound(RoundBoundingBox round) {
        return null; // optional
    }
}

