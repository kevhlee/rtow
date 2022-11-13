package com.khl.trace.render;

import com.khl.trace.geometry.Ray3;
import com.khl.trace.geometry.Vec3;

/**
 * A 3D camera for rendering scenes.
 *
 * @author Kevin Lee
 */
public class Camera {

    public static class Builder {

        private final Vec3 lookAt;
        private final Vec3 lookUp;
        private final Vec3 lookFrom;

        private double aperture;
        private double aspectRatio;
        private double fieldOfView;
        private double focusDistance;

        public Builder(Vec3 lookFrom, Vec3 lookAt, Vec3 lookUp) {
            this.lookFrom = lookFrom;
            this.lookAt = lookAt;
            this.lookUp = lookUp;
        }

        public Builder setAperture(double aperture) {
            this.aperture = aperture;
            return this;
        }

        public Builder setAspectRatio(double aspectRatio) {
            this.aspectRatio = aspectRatio;
            return this;
        }

        public Builder setFieldOfView(double fieldOfView) {
            this.fieldOfView = fieldOfView;
            return this;
        }

        public Builder setFocusDistance(double focusDistance) {
            this.focusDistance = focusDistance;
            return this;
        }

        public Camera build() {
            return new Camera(this);
        }

    }

    private final Vec3 u;
    private final Vec3 v;
    private final Vec3 w;
    private final Vec3 origin;
    private final Vec3 vertical;
    private final Vec3 horizontal;
    private final Vec3 lowerLeftCorner;
    private final double lensRadius;

    private Camera(Builder builder) {
        double theta = Math.toRadians(builder.fieldOfView);
        double h = Math.tan(theta / 2);
        double viewportHeight = 2.0 * h;
        double viewportWidth = builder.aspectRatio * viewportHeight;

        w = builder.lookFrom.sub(builder.lookAt).unit();
        u = builder.lookUp.cross(w).unit();
        v = w.cross(u);

        origin = builder.lookFrom;
        vertical = v.mul(viewportHeight * builder.focusDistance);
        horizontal = u.mul(viewportWidth * builder.focusDistance);
        lowerLeftCorner = origin.sub(horizontal.mul(0.5))
                .sub(vertical.mul(0.5))
                .sub(w.mul(builder.focusDistance));

        lensRadius = builder.aperture / 2.0;
    }

    public Ray3 buildRay(double s, double t) {
        Vec3 rd = Vec3.randUnitDisk().mul(lensRadius);
        Vec3 offset = u.mul(rd.getX()).add(v.mul(rd.getY()));

        Vec3 direction =
                lowerLeftCorner.add(horizontal.mul(s)).add(vertical.mul(t)).sub(origin).sub(offset);

        return new Ray3(origin.add(offset), direction);
    }

}
