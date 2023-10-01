package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A dielectric/transparent surface material.
 *
 * @author Kevin Lee
 */
public class Dielectric implements Material {

    private final double ir;

    public Dielectric(double ir) {
        this.ir = ir;
    }

    @Override
    public boolean scatter(Ray ray, HitRecord rec, Vec attenuation) {
        attenuation.setX(1.0);
        attenuation.setY(1.0);
        attenuation.setZ(1.0);

        var normal = rec.getNormal();
        var unitDir = ray.getDirection().unit();

        var cosTheta = Math.min(unitDir.neg().dot(normal), 1.0);
        var sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);
        var refractRatio = rec.isFrontFace() ? (1.0 / ir) : ir;

        Vec direction;
        if (refractRatio * sinTheta > 1.0 || reflectance(cosTheta, refractRatio) > Math.random()) {
            direction = Vec.reflect(unitDir, normal);
        } else {
            direction = Vec.refract(unitDir, normal, refractRatio);
        }

        ray.setOrigin(rec.getPoint());
        ray.setDirection(direction);

        return true;
    }

    private double reflectance(double cosine, double refractIndex) {
        var r0 = (1 - refractIndex) / (1 + refractIndex);
        r0 *= r0;
        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }

}