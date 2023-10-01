package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray3;
import com.khl.rtow.math.Vec3;

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

    private final Vec3 v0;
    private final Vec3 v1;
    private final Vec3 v2;
    private final boolean cull;

    public Triangle(Vec3 v0, Vec3 v1, Vec3 v2, Material material) {
        this(v0, v1, v2, true, material);
    }

    public Triangle(
            Vec3 v0, Vec3 v1, Vec3 v2, boolean cull, Material material) {

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
    public boolean hit(double tMin, double tMax, Ray3 ray, HitRecord rec) {
        Vec3 dir = ray.getDirection();
        Vec3 origin = ray.getOrigin();

        Vec3 v0v1 = v1.sub(v0);
        Vec3 v0v2 = v2.sub(v0);
        Vec3 pVec = dir.cross(v0v2);
        double det = v0v1.dot(pVec);

        // Check if triangle is backfacing if culling is enabled
        if (cull && det < 1e-6) {
            return false;
        }

        // Check if ray and triangle is parallel
        if (Math.abs(det) < 1e-6) {
            return false;
        }

        Vec3 tVec = origin.sub(v0);
        Vec3 qVec = tVec.cross(v0v1);

        double invDet = 1 / det;
        double u = tVec.dot(pVec) * invDet;
        double v = dir.dot(qVec) * invDet;
        if (u < 0 || u > 1 || v < 0 || u + v > 1) {
            return false;
        }

        double t = v0v2.dot(qVec) * invDet;
        return rec.record(t, tMin, tMax, ray, this);
    }

    @Override
    public Vec3 getSurfaceNormal(Vec3 point) {
        Vec3 v0v1 = v1.sub(v0);
        Vec3 v0v2 = v2.sub(v0);
        return v0v1.cross(v0v2).unit();
    }
}