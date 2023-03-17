package com.khl.trace.hittable;

import com.khl.trace.material.Material;

/**
 * An abstract base class for hittable objects.
 *
 * @author Kevin Lee
 */
public abstract class BaseHittable implements Hittable {

    private final Material material;

    public BaseHittable(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

}