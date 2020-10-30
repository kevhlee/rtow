package trace.hittable;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.material.Material;

/**
 * A hittable sphere.
 *
 * @author Kevin Lee
 */
public class Sphere implements Hittable {

    private final Vec3 center;
    private final double radius;
    private final Material material;

    public Sphere(double radius, Vec3 center, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public boolean hit(double tMin, double tMax, Ray3 ray, HitRecord record) {
        Vec3 oc = ray.getOrigin().sub(center);
        Vec3 dir = ray.getDirection();

        double a = dir.lengthSq();
        double halfB = oc.dot(dir);
        double c = oc.lengthSq() - (radius * radius);
        double discriminant = (halfB * halfB) - (a * c);

        if (discriminant > 0) {
            double root = Math.sqrt(discriminant);

            if (isHit((-halfB - root) / a, tMin, tMax, ray, record)) {
                return true;
            }
            if (isHit((-halfB + root) / a, tMin, tMax, ray, record)) {
                return true;
            }
        }

        return false;
    }

    private boolean isHit(
            double t, double tMin, double tMax, Ray3 ray, HitRecord record) {

        if (t >= tMax || t <= tMin) {
            return false;
        }

        Vec3 point = ray.at(t);
        Vec3 normal = point.sub(center).mul(1 / radius);

        record.record(ray, t, point, normal, material);

        return true;
    }

}
