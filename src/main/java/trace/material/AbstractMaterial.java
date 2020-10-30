package trace.material;

import trace.geometry.Vec3;

/**
 * An abstract base class for surface materials.
 *
 * @author Kevin Lee
 */
public abstract class AbstractMaterial implements Material {

    private final Vec3 albedo;

    public AbstractMaterial(Vec3 albedo) {
        this.albedo = albedo;
    }

    public Vec3 getAlbedo() {
        return albedo;
    }

}
