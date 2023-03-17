package com.khl.trace.render;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.hittable.HitRecord;
import com.khl.trace.hittable.Hittable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A set of hittable objects.
 *
 * @author Kevin Lee
 */
public class Scene implements Iterable<Hittable> {

    private final Set<Hittable> hittables;

    public Scene() {
        this.hittables = new HashSet<>();
    }

    public void add(Hittable hittable) {
        hittables.add(hittable);
    }

    public boolean hit(double tMin, double tMax, Ray3 ray, HitRecord rec) {
        boolean hit = false;
        double tClosest = tMax;

        for (Hittable hittable : this) {
            if (hittable.hit(tMin, tClosest, ray, rec)) {
                hit = true;
                tClosest = rec.getT();
            }
        }

        return hit;
    }

    @Override
    public Iterator<Hittable> iterator() {
        return hittables.iterator();
    }

}