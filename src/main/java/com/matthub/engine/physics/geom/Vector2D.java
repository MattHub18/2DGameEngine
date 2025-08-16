package com.matthub.engine.physics.geom;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    // Zero vector
    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }

    // Add another vector
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    // Subtract another vector
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    // Multiply by scalar
    public Vector2D scale(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    // Length (magnitude)
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSquared() {
        return length() * length();
    }

    // Normalize (unit vector)
    public Vector2D normalize() {
        float len = length();
        return (len == 0) ? new Vector2D(0, 0) : new Vector2D(x / len, y / len);
    }

    // Dot product
    public float dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
