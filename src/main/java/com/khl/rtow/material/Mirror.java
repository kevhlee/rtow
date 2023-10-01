package com.khl.rtow.material;

import com.khl.rtow.math.Vec;

/**
 * A purely reflective surface material.
 *
 * @author Kevin Lee
 */
public class Mirror extends Reflective {

    public Mirror() {
        super(new Vec(1, 1, 1));
    }

}