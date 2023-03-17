package com.khl.trace.material;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;
import com.khl.trace.hittable.HitRecord;

/**
 * A reflective surface material.
 *
 * @author Kevin Lee
 */
public class Reflective extends Albedo {

    public Reflective(Vec3 albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation) {
        Vec3 normal = rec.getNormal();
        Vec3 reflectDir = Vec3.reflect(ray.getDirection().unit(), normal);

        if (reflectDir.dot(normal) > 0) {
            ray.setOrigin(rec.getPoint());
            ray.setDirection(reflectDir);

            Vec3 albedo = getAlbedo();

            attenuation.setX(albedo.getX());
            attenuation.setY(albedo.getY());
            attenuation.setZ(albedo.getZ());

            return true;
        }

        return false;
    }

}