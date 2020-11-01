package trace.material;

import trace.geometry.Vec3;

/**
 * An abstract base class for surface materials with albedo.
 *
 * @author Kevin Lee
 */
public abstract class AlbedoMaterial implements Material {

    private final Vec3 albedo;

    public AlbedoMaterial(Vec3 albedo) {
        this.albedo = albedo;
    }

    public Vec3 getAlbedo() {
        return albedo;
    }

}
