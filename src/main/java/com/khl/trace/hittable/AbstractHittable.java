package com.khl.trace.hittable;

import com.khl.trace.material.Material;

/**
 * An abstract base class for hittable objects.
 *
 * @author Kevin Lee
 */
public abstract class AbstractHittable implements Hittable {

    private final Material material;

    public AbstractHittable(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

}
