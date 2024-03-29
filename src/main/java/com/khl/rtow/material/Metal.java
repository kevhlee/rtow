package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A metallic surface material.
 *
 * @author Kevin Lee
 */
public class Metal extends Reflective {

    private final double fuzz;

    public Metal(Vec albedo, double fuzz) {
        super(albedo);
        this.fuzz = Math.min(fuzz, 1);
    }

    @Override
    public boolean scatter(Ray ray, HitRecord rec, Vec attenuation) {
        if (super.scatter(ray, rec, attenuation)) {
            if (fuzz > 0.0) {
                ray.setDirection(ray.getDirection().add(fuzzVector()));
            }
            return true;
        }
        return false;
    }

    private Vec fuzzVector() {
        return Vec.randUnitSphere().mul(fuzz);
    }

}