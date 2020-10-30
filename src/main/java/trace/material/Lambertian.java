package trace.material;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;

/**
 * A Lambertian-based diffuse surface material.
 *
 * @author Kevin Lee
 */
public class Lambertian extends AbstractMaterial {

    public Lambertian(Vec3 albedo) {
        super(albedo);
    }

    @Override
    public boolean scatter(Ray3 ray, HitRecord record, Vec3 attenuation) {
        ray.setOrigin(record.getPoint());
        ray.setDirection(record.getNormal().add(Vec3.randUnit()));

        Vec3 albedo = getAlbedo();

        attenuation.setX(albedo.getX());
        attenuation.setY(albedo.getY());
        attenuation.setZ(albedo.getZ());

        return true;
    }

}
