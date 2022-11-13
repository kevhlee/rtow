package com.khl.trace.hittable;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.material.Material;

/**
 * An interface for ray-hittable objects.
 *
 * @author Kevin Lee
 */
public interface Hittable {

    boolean hit(double tMin, double tMax, Ray3 ray, HitRecord rec);

    Vec3 getSurfaceNormal(Vec3 point);

    Material getMaterial();

}
