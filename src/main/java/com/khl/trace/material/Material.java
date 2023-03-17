package com.khl.trace.material;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.hittable.HitRecord;

/**
 * The surface material of a hittable object.
 *
 * @author Kevin Lee
 */
public interface Material {

    boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation);

}