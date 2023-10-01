package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A hittable 3D plane.
 *
 * @author Kevin Lee
 */
public class Plane extends BaseHittable {

    private final Vec origin;
    private final Vec normal;

    public Plane(Vec origin, Vec normal, Material material) {
        super(material);

        this.origin = origin;
        this.normal = normal.unit();
    }

    @Override
    public boolean hit(double tMin, double tMax, Ray ray, HitRecord rec) {
        var d = normal.dot(ray.getDirection());
        if (d > 1e-6) {
            double t = origin.sub(ray.getOrigin()).dot(normal) / d;
            return rec.record(t, tMin, tMax, ray, this);
        }
        return false;
    }

    @Override
    public Vec getSurfaceNormal(Vec point) {
        return normal;
    }

}