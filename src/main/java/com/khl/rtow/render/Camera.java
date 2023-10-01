package com.khl.rtow.render;

import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

/**
 * A 3D camera for rendering scenes.
 *
 * @author Kevin Lee
 */
public class Camera {

    public static final double DEFAULT_APERTURE = 0.1;
    public static final double DEFAULT_FIELD_OF_VIEW = 20;
    public static final double DEFAULT_FOCUS_DISTANCE = 10;

    private double aperture;
    private double fieldOfView;
    private double focusDistance;

    private final Vec origin;
    private final Vec lookAt;
    private final Vec lookUp;
    private Vec u;
    private Vec v;
    private Vec vertical;
    private Vec horizontal;
    private Vec lowerLeftCorner;
    private double lensRadius;

    public Camera(Vec origin, Vec lookAt, Vec lookUp) {
        this(origin, lookAt, lookUp, DEFAULT_APERTURE, DEFAULT_FIELD_OF_VIEW, DEFAULT_FOCUS_DISTANCE);
    }

    public Camera(Vec origin, Vec lookAt, Vec lookUp, double aperture, double fieldOfView, double focusDistance) {
        this.aperture = aperture;
        this.fieldOfView = fieldOfView;
        this.focusDistance = focusDistance;

        this.origin = origin;
        this.lookAt = lookAt;
        this.lookUp = lookUp;
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

    public double getAperture() {
        return aperture;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public double getFocusDistance() {
        return focusDistance;
    }

    public Vec getOrigin() {
        return origin;
    }

    public Camera setAperture(double aperture) {
        this.aperture = aperture;
        return this;
    }

    public Camera setFieldOfView(double fieldOfView) {
        this.fieldOfView = fieldOfView;
        return this;
    }

    public Camera setFocusDistance(double focusDistance) {
        this.focusDistance = focusDistance;
        return this;
    }

    protected void initialize(int width, int height) {
        var aspectRatio = (double) width / height;
        var theta = Math.toRadians(fieldOfView);
        var h = Math.tan(theta / 2);
        var viewportHeight = 2.0 * h;
        var viewportWidth = aspectRatio * viewportHeight;
        var w = origin.sub(lookAt).unit();

        u = lookUp.cross(w).unit();
        v = w.cross(u);
        vertical = v.mul(viewportHeight * focusDistance);
        horizontal = u.mul(viewportWidth * focusDistance);

        lowerLeftCorner = origin.sub(horizontal.mul(0.5))
                .sub(vertical.mul(0.5))
                .sub(w.mul(focusDistance));

        lensRadius = aperture / 2.0;
    }

}