package com.khl.rtow.math;

/**
 * A 3D vector.
 *
 * @author Kevin Lee
 */
public class Vec {

    private double x;
    private double y;
    private double z;

    public static Vec rand() {
        return rand(0, 1);
    }

    public static Vec rand(double min, double max) {
        return new Vec(
                (max - min) * Math.random() + min,
                (max - min) * Math.random() + min,
                (max - min) * Math.random() + min);
    }

    public static Vec randUnitSphere() {
        var point = rand(-1, 1);
        while (point.lengthSq() >= 1) {
            point = rand(-1, 1);
        }
        return point;
    }

    public static Vec randUnit() {
        var a = Math.random() * 2 * Math.PI;
        var z = 2 * Math.random() - 1;
        var r = Math.sqrt(1 - z * z);
        return new Vec(r * Math.cos(a), r * Math.sin(a), z);
    }

    public static Vec randHemisphere(Vec normal) {
        var inUnitSphere = randUnitSphere();
        if (inUnitSphere.dot(normal) <= 0.0) {
            return inUnitSphere.neg();
        }
        return inUnitSphere;
    }

    public static Vec randUnitDisk() {
        var point = new Vec(2 * Math.random() - 1, 2 * Math.random() - 1, 0);
        while (point.lengthSq() >= 1) {
            point.setX(2 * Math.random() - 1);
            point.setY(2 * Math.random() - 1);
        }
        return point;
    }

    public static Vec reflect(Vec v, Vec normal) {
        return v.sub(normal.mul(2 * v.dot(normal)));
    }

    public static Vec refract(Vec uv, Vec normal, double refractRatio) {
        var cosTheta = -uv.dot(normal);
        var rOutPerp = uv.add(normal.mul(cosTheta)).mul(refractRatio);
        var rOutParallel = normal.mul(-Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSq())));
        return rOutPerp.add(rOutParallel);
    }

    public Vec(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vec neg() {
        return new Vec(-x, -y, -z);
    }

    public Vec unit() {
        return mul(1.0 / length());
    }

    public Vec add(Vec other) {
        return new Vec(x + other.x, y + other.y, z + other.z);
    }

    public Vec sub(Vec other) {
        return new Vec(x - other.x, y - other.y, z - other.z);
    }

    public Vec mul(double scalar) {
        return new Vec(x * scalar, y * scalar, z * scalar);
    }

    public Vec mul(Vec other) {
        return new Vec(x * other.x, y * other.y, z * other.z);
    }

    public void addInPlace(Vec other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public double dot(Vec other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vec cross(Vec other) {
        return new Vec(
                (y * other.z) - (z * other.y),
                (z * other.x) - (x * other.z),
                (x * other.y) - (y * other.x));
    }

    public double length() {
        return Math.sqrt(lengthSq());
    }

    public double lengthSq() {
        return (x * x) + (y * y) + (z * z);
    }

    @Override
    public String toString() {
        return String.format("Vec3[ %.3f %.3f %.3f ]", x, y, z);
    }

}