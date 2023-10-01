package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray3;
import com.khl.rtow.math.Vec3;

/**
 * A Lambertian-based diffuse surface material.
 *
 * @author Kevin Lee
 */
public class Lambertian extends Albedo {

    public Lambertian(Vec3 albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation) {
        ray.setOrigin(rec.getPoint());
        ray.setDirection(rec.getNormal().add(Vec3.randUnit()));

        Vec3 albedo = getAlbedo();

        attenuation.setX(albedo.getX());
        attenuation.setY(albedo.getY());
        attenuation.setZ(albedo.getZ());

        return true;
    }

}