package trace.material;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;

/**
 * The surface material of a hittable object.
 *
 * @author Kevin Lee
 */
public interface Material {

    boolean scatter(Ray3 ray, HitRecord rec, Vec3 attenuation);

}
