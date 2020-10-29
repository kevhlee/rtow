package trace.hittable;

import trace.geometry.Ray3;

import java.util.HashSet;

/**
 * A set of hittable objects.
 *
 * @author Kevin Lee
 */
public class HittableSet extends HashSet<Hittable> implements Hittable {

    @Override
    public boolean hit(double tMin, double tMax, Ray3 ray, HitRecord record) {
        double tClosest = tMax;
        boolean hitAnything = false;

        for (Hittable hittable : this) {
            if (hittable.hit(tMin, tClosest, ray, record)) {
                hitAnything = true;
                tClosest = record.getT();
            }
        }

        return hitAnything;
    }

}
