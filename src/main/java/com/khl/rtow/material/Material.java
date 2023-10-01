package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * The surface material of a hittable object.
 *
 * @author Kevin Lee
 */
public interface Material {

    boolean scatter(Ray ray, HitRecord rec, Vec attenuation);

}