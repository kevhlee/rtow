package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray3;
import com.khl.rtow.math.Vec3;

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