package com.khl.trace.hittable;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.material.Material;

/**
 * A hittable 3D plane.
 *
 * @author Kevin Lee
 */
public class Plane extends BaseHittable {

    private final Vec3 origin;
    private final Vec3 normal;

    public Plane(Vec3 origin, Vec3 normal, Material material) {
        super(material);

        this.origin = origin;
        this.normal = normal.unit();
    }

    @Override
    public boolean hit(double tMin, double tMax, Ray3 ray, HitRecord rec) {
        double d = normal.dot(ray.getDirection());
        if (d > 1e-6) {
            double t = origin.sub(ray.getOrigin()).dot(normal) / d;
            return rec.record(t, tMin, tMax, ray, this);
        }
        return false;
    }

    @Override
    public Vec3 getSurfaceNormal(Vec3 point) {
        return normal;
    }

}