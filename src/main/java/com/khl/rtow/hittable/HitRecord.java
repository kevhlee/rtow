package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A class used for storing information about ray-intersections.
 *
 * @author Kevin Lee
 */
public class HitRecord {

    private double t;
    private Vec point;
    private Vec normal;
    private boolean frontFace;
    private Material material;

    public double getT() {
        return t;
    }

    public Vec getPoint() {
        return point;
    }

    public Vec getNormal() {
        return normal;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isFrontFace() {
        return frontFace;
    }

    public boolean record(double t, double tMin, double tMax, Ray ray, Hittable hittable) {
        if (t <= tMin || t >= tMax) {
            return false;
        }

        var point = ray.at(t);
        var outwardNormal = hittable.getSurfaceNormal(point);

        this.t = t;
        this.point = point;
        this.material = hittable.getMaterial();
        this.frontFace = ray.getDirection().dot(outwardNormal) < 0.0;
        this.normal = (frontFace) ? outwardNormal : outwardNormal.neg();

        return true;
    }

}