package com.khl.rtow.math;

/**
 * A 3D ray.
 *
 * @author Kevin Lee
 */
public class Ray3 {

    private Vec3 origin;
    private Vec3 direction;

    public Ray3(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 at(double t) {
        return origin.add(direction.mul(t));
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public void setOrigin(Vec3 origin) {
        this.origin = origin;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
    }

}