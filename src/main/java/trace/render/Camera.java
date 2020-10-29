package trace.render;

import trace.geometry.Ray3;
import trace.geometry.Vec3;

/**
 * A 3D camera for rendering scenes.
 *
 * @author Kevin Lee
 */
public class Camera {

    private final Vec3 origin;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 lowerLeftCorner;

    public Camera(double aspectRatio) {
        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        this.origin = new Vec3(0, 0, 0);
        this.horizontal = new Vec3(viewportWidth, 0, 0);
        this.vertical = new Vec3(0, viewportHeight, 0);

        Vec3 lowerLeftCorner = origin.sub(horizontal.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(vertical.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(new Vec3(0, 0, focalLength));
        this.lowerLeftCorner = lowerLeftCorner;
    }

    public Ray3 buildRay(double u, double v) {
        Vec3 direction = lowerLeftCorner.add(horizontal.mul(u));
        direction = direction.add(vertical.mul(v));
        direction = direction.sub(origin);

        return new Ray3(origin, direction);
    }

}
