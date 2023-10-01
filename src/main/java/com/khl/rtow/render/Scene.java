package com.khl.rtow.render;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.hittable.Hittable;
import com.khl.rtow.math.Ray;

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

    public boolean hit(double tMin, double tMax, Ray ray, HitRecord rec) {
        var hit = false;
        var tClosest = tMax;

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