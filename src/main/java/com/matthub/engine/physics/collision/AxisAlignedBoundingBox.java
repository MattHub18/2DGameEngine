package com.matthub.engine.physics.collision;

import com.matthub.engine.physics.geom.Vector2D;


public class AxisAlignedBoundingBox implements Collider {

    private Vector2D center;
    private final Vector2D halfSize;

    public AxisAlignedBoundingBox(Vector2D min, Vector2D max) {
        Vector2D size = max.subtract(min);
        this.halfSize = size.scale(0.5f);
        this.center = min.add(halfSize);
    }

    public Vector2D getCenter() {
        return new Vector2D(center);
    }

    public Vector2D getMin() {
        return center.subtract(halfSize);
    }

    public Vector2D getMax() {
        return center.add(halfSize);
    }

    public void translate(Vector2D delta) {
        center = center.add(delta);
    }

    @Override
    public boolean collision(Collider other) {
        return other.collisionWithAABB(this);
    }

    @Override
    public boolean collisionWithAABB(AxisAlignedBoundingBox other) {
        Vector2D aMin = this.getMin();
        Vector2D aMax = this.getMax();
        Vector2D bMin = other.getMin();
        Vector2D bMax = other.getMax();

        return aMin.x < bMax.x && aMax.x > bMin.x &&
                aMin.y < bMax.y && aMax.y > bMin.y;
    }

    @Override
    public boolean collisionWithRound(RoundBoundingBox round) {
        return round.collisionWithAABB(this);
    }

    @Override
    public Collider intersect(Collider other) {
        return other.intersectWithAABB(this);
    }

    @Override
    public Collider intersectWithAABB(AxisAlignedBoundingBox other) {
        float minX = Math.max(this.getMin().x, other.getMin().x);
        float minY = Math.max(this.getMin().y, other.getMin().y);
        float maxX = Math.min(this.getMax().x, other.getMax().x);
        float maxY = Math.min(this.getMax().y, other.getMax().y);

        if (minX >= maxX || minY >= maxY)
            return null;
        return new AxisAlignedBoundingBox(new Vector2D(minX, minY), new Vector2D(maxX, maxY));
    }

    @Override
    public Collider intersectWithRound(RoundBoundingBox round) {
        return null;
    }
}
