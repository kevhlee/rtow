package trace.material;

import trace.geometry.Vec3;

/**
 * A purely reflective surface material.
 *
 * @author Kevin Lee
 */
public class Mirror extends Reflective {

    public Mirror() {
        super(new Vec3(1, 1, 1));
    }

}
