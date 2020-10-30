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

    public static Vec3 rand() {
        return rand(0, 1);
    }

    public static Vec3 rand(double min, double max) {
        double x = (max - min) * Math.random() + min;
        double y = (max - min) * Math.random() + min;
        double z = (max - min) * Math.random() + min;

        return new Vec3(x, y, z);
    }

    public static Vec3 randUnitSphere() {
        Vec3 point = rand(-1, 1);

        while (point.lengthSq() >= 1) {
            point = rand(-1, 1);
        }

        return point;
    }

    public static Vec3 randUnit() {
        double a = Math.random() * 2 * Math.PI;
        double z = 2 * Math.random() - 1;
        double r = Math.sqrt(1 - z * z);

        return new Vec3(r * Math.cos(a), r * Math.sin(a), z);
    }

    public static Vec3 randHemisphere(Vec3 normal) {
        Vec3 inUnitSphere = randUnitSphere();

        if (inUnitSphere.dot(normal) <= 0.0) {
            return inUnitSphere.neg();
        }

        return inUnitSphere;
    }

    public static Vec3 reflect(Vec3 v, Vec3 normal) {
        return v.sub(normal.mul(2 * v.dot(normal)));
    }

    public static Vec3 refract(Vec3 uv, Vec3 normal, double refractRatio) {
        double cosTheta = -uv.dot(normal);

        Vec3 rOutPerp = uv.add(normal.mul(cosTheta)).mul(refractRatio);
        Vec3 rOutParallel =
                normal.mul(-Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSq())));

        return rOutPerp.add(rOutParallel);
    }

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
