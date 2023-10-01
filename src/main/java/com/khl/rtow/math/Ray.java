package com.khl.rtow.math;

/**
 * A 3D ray.
 *
 * @author Kevin Lee
 */
public class Ray {

    private Vec origin;
    private Vec direction;

    public Ray(Vec origin, Vec direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec at(double t) {
        return origin.add(direction.mul(t));
    }

    public Vec getOrigin() {
        return origin;
    }

    public Vec getDirection() {
        return direction;
    }

    public void setOrigin(Vec origin) {
        this.origin = origin;
    }

    public void setDirection(Vec direction) {
        this.direction = direction;
    }

}