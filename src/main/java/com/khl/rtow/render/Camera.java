package com.khl.rtow.render;

import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A 3D camera for rendering scenes.
 *
 * @author Kevin Lee
 */
public class Camera {

    private final Vec u;
    private final Vec v;
    private final Vec w;
    private final Vec origin;
    private final Vec vertical;
    private final Vec horizontal;
    private final Vec lowerLeftCorner;
    private final double lensRadius;

    private Camera(Builder builder) {
        var theta = Math.toRadians(builder.fieldOfView);
        var h = Math.tan(theta / 2);
        var viewportHeight = 2.0 * h;
        var viewportWidth = builder.aspectRatio * viewportHeight;

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

    public static Builder builder(Vec lookFrom, Vec lookAt, Vec lookUp) {
        return new Builder(lookFrom, lookAt, lookUp);
    }

    public Ray generateRay(double s, double t) {
        var rd = Vec.randUnitDisk().mul(lensRadius);
        var offset = u.mul(rd.getX()).add(v.mul(rd.getY()));

        var direction = lowerLeftCorner.add(horizontal.mul(s))
                .add(vertical.mul(t))
                .sub(origin)
                .sub(offset);

        return new Ray(origin.add(offset), direction);
    }

    public static class Builder {

        private final Vec lookFrom;
        private final Vec lookAt;
        private final Vec lookUp;

        private double aperture;
        private double aspectRatio;
        private double fieldOfView;
        private double focusDistance;

        private Builder(Vec lookFrom, Vec lookAt, Vec lookUp) {
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

}