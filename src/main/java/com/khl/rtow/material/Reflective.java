package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A reflective surface material.
 *
 * @author Kevin Lee
 */
public class Reflective extends Albedo {

    public Reflective(Vec albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray ray, HitRecord rec, Vec attenuation) {
        var normal = rec.getNormal();
        var reflectDir = Vec.reflect(ray.getDirection().unit(), normal);

        if (reflectDir.dot(normal) > 0) {
            ray.setOrigin(rec.getPoint());
            ray.setDirection(reflectDir);

            var albedo = getAlbedo();
            attenuation.setX(albedo.getX());
            attenuation.setY(albedo.getY());
            attenuation.setZ(albedo.getZ());

            return true;
        }

        return false;
    }

}