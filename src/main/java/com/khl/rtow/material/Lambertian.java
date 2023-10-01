package com.khl.rtow.material;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A Lambertian-based diffuse surface material.
 *
 * @author Kevin Lee
 */
public class Lambertian extends Albedo {

    public Lambertian(Vec albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray ray, HitRecord rec, Vec attenuation) {
        ray.setOrigin(rec.getPoint());
        ray.setDirection(rec.getNormal().add(Vec.randUnit()));

        var albedo = getAlbedo();
        attenuation.setX(albedo.getX());
        attenuation.setY(albedo.getY());
        attenuation.setZ(albedo.getZ());

        return true;
    }

}