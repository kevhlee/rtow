package trace.material;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;

/**
 * A metallic surface material.
 *
 * @author Kevin Lee
 */
public class Metal extends AbstractMaterial {

    private final double fuzz;

    public Metal(Vec3 albedo, double fuzz) {
        super(albedo);

        this.fuzz = Math.min(fuzz, 1);
    }

    @Override
    public boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation) {
        Vec3 normal = rec.getNormal();
        Vec3 reflectDir = Vec3.reflect(ray.getDirection().unit(), normal);

        if (reflectDir.dot(normal) > 0) {
            ray.setOrigin(rec.getPoint());
            ray.setDirection(
                    reflectDir.add(Vec3.randUnitSphere().mul(fuzz)));

            Vec3 albedo = getAlbedo();

            attenuation.setX(albedo.getX());
            attenuation.setY(albedo.getY());
            attenuation.setZ(albedo.getZ());

            return true;
        }

        return false;
    }

}
