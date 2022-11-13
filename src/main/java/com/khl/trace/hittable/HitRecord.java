package com.khl.trace.hittable;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.material.Material;

/**
 * A class used for storing information about ray-intersections.
 *
 * @author Kevin Lee
 */
public class HitRecord {

    private double t;
    private Vec3 point;
    private Vec3 normal;
    private boolean frontFace;
    private Material material;

    public double getT() {
        return t;
    }

    public Vec3 getPoint() {
        return point;
    }

    public Vec3 getNormal() {
        return normal;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isFrontFace() {
        return frontFace;
    }

    public boolean record(double t, double tMin, double tMax, Ray3 ray, Hittable hittable) {
        if (t <= tMin || t >= tMax) {
            return false;
        }

        Vec3 point = ray.at(t);
        Vec3 outwardNormal = hittable.getSurfaceNormal(point);

        this.t = t;
        this.point = point;
        this.material = hittable.getMaterial();
        this.frontFace = ray.getDirection().dot(outwardNormal) < 0.0;
        this.normal = (frontFace) ? outwardNormal : outwardNormal.neg();

        return true;
    }

}
