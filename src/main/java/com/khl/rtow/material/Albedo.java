package com.khl.rtow.material;

import com.khl.rtow.math.Vec;

/**
 * An abstract base class for surface materials with albedo.
 *
 * @author Kevin Lee
 */
public abstract class Albedo implements Material {

    private final Vec albedo;

    public Albedo(Vec albedo) {
        this.albedo = albedo;
    }

    public Vec getAlbedo() {
        return albedo;
    }

}