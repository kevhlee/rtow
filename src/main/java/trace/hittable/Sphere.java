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

            double t0 = (-halfB - root) / a;
            double t1 = (-halfB + root) / a;

            if (t0 < tMax && t0 > tMin) {
                return record.record(t0, tMin, tMax, ray, this);
            }
            if (t1 < tMax && t1 > tMin) {
                return record.record(t1, tMin, tMax, ray, this);
            }
        }

        return false;
    }

    @Override
    public Vec3 getSurfaceNormal(Vec3 point) {
        return point.sub(center).mul(1 / radius);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

}
