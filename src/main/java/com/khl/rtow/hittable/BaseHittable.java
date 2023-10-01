package com.khl.rtow.hittable;

import com.khl.rtow.material.Material;

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