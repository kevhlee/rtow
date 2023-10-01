package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A hittable triangle in 3D space.
 * <p>
 * Note that the coordinates of the triangle's vertices should be
 * specified in counter-clockwise fashion. This implementation also
 * allows the user to enable backface culling.
 *
 * @author Kevin Lee
 */
public class Triangle extends BaseHittable {

    private final Vec v0;
    private final Vec v1;
    private final Vec v2;
    private final boolean cull;

    public Triangle(Vec v0, Vec v1, Vec v2, Material material) {
        this(v0, v1, v2, true, material);
    }

    public Triangle(
            Vec v0, Vec v1, Vec v2, boolean cull, Material material) {

        super(material);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.cull = cull;
    }

    public boolean isSingleSided() {
        return cull;
    }

    @Override
    public boolean hit(double tMin, double tMax, Ray ray, HitRecord rec) {
        var dir = ray.getDirection();
        var origin = ray.getOrigin();

        var v0v1 = v1.sub(v0);
        var v0v2 = v2.sub(v0);
        var pVec = dir.cross(v0v2);
        var det = v0v1.dot(pVec);

        // Check if triangle is backfacing if culling is enabled
        if (cull && det < 1e-6) {
            return false;
        }

        // Check if ray and triangle is parallel
        if (Math.abs(det) < 1e-6) {
            return false;
        }

        var tVec = origin.sub(v0);
        var qVec = tVec.cross(v0v1);

        var invDet = 1 / det;
        var u = tVec.dot(pVec) * invDet;
        var v = dir.dot(qVec) * invDet;
        if (u < 0 || u > 1 || v < 0 || u + v > 1) {
            return false;
        }

        var t = v0v2.dot(qVec) * invDet;
        return rec.record(t, tMin, tMax, ray, this);
    }

    @Override
    public Vec getSurfaceNormal(Vec point) {
        Vec v0v1 = v1.sub(v0);
        Vec v0v2 = v2.sub(v0);
        return v0v1.cross(v0v2).unit();
    }
}