package trace.hittable;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.material.Material;

/**
 * An interface for ray-hittable objects.
 *
 * @author Kevin Lee
 */
public interface Hittable {

    boolean hit(double tMin, double tMax, Ray3 ray, HitRecord record);

    Vec3 getSurfaceNormal(Vec3 point);

    Material getMaterial();

}
