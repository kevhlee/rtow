package trace.material;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;

/**
 * A metallic surface material.
 *
 * @author Kevin Lee
 */
public class Metal extends Reflective {

    private final double fuzz;

    public Metal(Vec3 albedo, double fuzz) {
        super(albedo);

        this.fuzz = Math.min(fuzz, 1);
    }

    @Override
    public boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation) {
        if (super.scatter(ray, rec, attenuation)) {
            if (fuzz > 0.0) {
                ray.setDirection(ray.getDirection().add(fuzzVector()));
            }

            return true;
        }

        return false;
    }

    private Vec3 fuzzVector() {
        return Vec3.randUnitSphere().mul(fuzz);
    }

}
