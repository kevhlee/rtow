package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * An interface for ray-hittable objects.
 *
 * @author Kevin Lee
 */
public interface Hittable {

    boolean hit(double tMin, double tMax, Ray ray, HitRecord rec);

    Vec getSurfaceNormal(Vec point);

    Material getMaterial();

}