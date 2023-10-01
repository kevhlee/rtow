package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A hittable sphere.
 *
 * @author Kevin Lee
 */
public class Sphere extends BaseHittable {

    private final Vec center;
    private final double radius;

    public Sphere(double radius, Vec center, Material material) {
        super(material);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean hit(double tMin, double tMax, Ray ray, HitRecord rec) {
        var oc = ray.getOrigin().sub(center);
        var dir = ray.getDirection();

        var a = dir.lengthSq();
        var halfB = oc.dot(dir);
        var c = oc.lengthSq() - (radius * radius);
        var discriminant = (halfB * halfB) - (a * c);

        if (discriminant > 0) {
            var root = Math.sqrt(discriminant);
            var t0 = (-halfB - root) / a;
            var t1 = (-halfB + root) / a;

            if (t0 < tMax && t0 > tMin) {
                return rec.record(t0, tMin, tMax, ray, this);
            }
            if (t1 < tMax && t1 > tMin) {
                return rec.record(t1, tMin, tMax, ray, this);
            }
        }
        return false;
    }

    @Override
    public Vec getSurfaceNormal(Vec point) {
        return point.sub(center).mul(1 / radius);
    }

}