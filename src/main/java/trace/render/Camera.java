package trace.render;

import trace.geometry.Ray3;
import trace.geometry.Vec3;

/**
 * A 3D camera for rendering scenes.
 *
 * @author Kevin Lee
 */
public class Camera {

    private final Vec3 u;
    private final Vec3 v;
    private final Vec3 w;
    private final Vec3 origin;
    private final Vec3 horizontal;
    private final Vec3 vertical;
    private final Vec3 lowerLeftCorner;
    private final double lensRadius;

    public Camera(
            Vec3 from, Vec3 at, Vec3 up, double vfov, double aspectRatio,
            double aperture, double focusDistance) {

        double theta = Math.toRadians(vfov);
        double h = Math.tan(theta / 2);
        double viewHeight = 2.0 * h;
        double viewWidth = aspectRatio * viewHeight;

        this.w = from.sub(at).unit();
        this.u = up.cross(this.w).unit();
        this.v = this.w.cross(this.u);

        this.origin = from;
        this.horizontal = this.u.mul(viewWidth * focusDistance);
        this.vertical = this.v.mul(viewHeight * focusDistance);

        Vec3 lowerLeftCorner = origin.sub(horizontal.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(vertical.mul(0.5));
        lowerLeftCorner = lowerLeftCorner.sub(this.w.mul(focusDistance));
        this.lowerLeftCorner = lowerLeftCorner;

        this.lensRadius = aperture / 2.0;
    }

    public Ray3 buildRay(double s, double t) {
        Vec3 rd = Vec3.randUnitDisk().mul(lensRadius);
        Vec3 offset = u.mul(rd.getX()).add(v.mul(rd.getY()));

        Vec3 direction = lowerLeftCorner.add(horizontal.mul(s));
        direction = direction.add(vertical.mul(t));
        direction = direction.sub(origin);
        direction = direction.sub(offset);

        return new Ray3(origin.add(offset), direction);
    }

}
