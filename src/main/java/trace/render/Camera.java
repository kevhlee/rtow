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

    public Camera(
            Vec3 lookFrom, Vec3 lookAt, Vec3 up, double vfov,
            double aspectRatio) {

        double theta = Math.toRadians(vfov);
        double h = Math.tan(theta / 2);
        double viewportHeight = 2.0 * h;
        double viewportWidth = aspectRatio * viewportHeight;

        Vec3 w = lookFrom.sub(lookAt).unit();
        Vec3 u = up.cross(w).unit();
        Vec3 v = w.cross(u);

        this.origin = lookFrom;
        this.horizontal = u.mul(viewportWidth);
        this.vertical = v.mul(viewportHeight);

        Vec3 lowerLeftCorner = origin.sub(horizontal.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(vertical.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(w);
        this.lowerLeftCorner = lowerLeftCorner;
    }

    public Ray3 buildRay(double s, double t) {
        Vec3 direction = lowerLeftCorner.add(horizontal.mul(s));
        direction = direction.add(vertical.mul(t));
        direction = direction.sub(origin);

        return new Ray3(origin, direction);
    }

}
