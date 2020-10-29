package trace.hittable;

import trace.geometry.Ray3;

/**
 * An interface for ray-hittable objects.
 *
 * @author Kevin Lee
 */
public interface Hittable {

    boolean hit(double tMin, double tMax, Ray3 ray, HitRecord record);

}
