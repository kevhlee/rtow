package trace.geometry;

/**
 * A 3D vector.
 *
 * @author Kevin Lee
 */
public class Vec3 {

    private double x;
    private double y;
    private double z;

    public Vec3(double x, double y, double z) {
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vec3 neg() {
        return new Vec3(-x, -y, -z);
    }

    public Vec3 unit() {
        return mul(1.0 / length());
    }

    public Vec3 add(Vec3 other) {
        return new Vec3(x + other.x, y + other.y, z + other.z);
    }

    public Vec3 sub(Vec3 other) {
        return new Vec3(x - other.x, y - other.y, z - other.z);
    }

    public Vec3 mul(double scalar) {
        return new Vec3(x * scalar, y * scalar, z * scalar);
    }

    public Vec3 mul(Vec3 other) {
        return new Vec3(x * other.x, y * other.y, z * other.z);
    }

    public double dot(Vec3 other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vec3 cross(Vec3 other) {
        return new Vec3(
                (y * other.z) - (z * other.y),
                (z * other.x) - (x * other.z),
                (x * other.y) - (y * other.x)
        );
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
