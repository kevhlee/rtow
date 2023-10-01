package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray3;
import com.khl.rtow.math.Vec3;

/**
 * The surface material of a hittable object.
 *
 * @author Kevin Lee
 */
public interface Material {

    boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation);

}