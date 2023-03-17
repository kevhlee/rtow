package com.khl.trace.material;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.hittable.HitRecord;

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
    public boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation) {
        attenuation.setX(1.0);
        attenuation.setY(1.0);
        attenuation.setZ(1.0);

        Vec3 normal = rec.getNormal();
        Vec3 unitDir = ray.getDirection().unit();

        double cosTheta = Math.min(unitDir.neg().dot(normal), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);
        double refractRatio = rec.isFrontFace() ? (1.0 / ir) : ir;

        Vec3 direction;

        if (refractRatio * sinTheta > 1.0 ||
                reflectance(cosTheta, refractRatio) > Math.random()) {

            direction = Vec3.reflect(unitDir, normal);
        } else {
            direction = Vec3.refract(unitDir, normal, refractRatio);
        }

        ray.setOrigin(rec.getPoint());
        ray.setDirection(direction);

        return true;
    }

    private double reflectance(double cosine, double refractIndex) {
        double r0 = (1 - refractIndex) / (1 + refractIndex);
        r0 *= r0;
        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }

}