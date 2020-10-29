package trace.render;

import trace.geometry.Ray3;
import trace.geometry.Vec3;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * A ray-tracing tracer.
 *
 * @author Kevin Lee
 */
public class Renderer {

    private final Camera camera;

    public Renderer(Camera camera) {
        this.camera = camera;
    }

    public RenderedImage render() {
        int width = camera.getWidth();
        int height = camera.getHeight();

        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            System.out.print(
                    "\rScanlines remaining: " + (height - y - 1) + " \b");

            for (int x = 0; x < width; x++) {
                Ray3 ray = camera.buildRay(x, y);

                bufferedImage.setRGB(
                        x, height - (y + 1), toRGB(rayColor(ray)));
            }
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    private boolean hitSphere(Vec3 center, double radius, Ray3 ray) {
        Vec3 oc = ray.getOrigin().sub(center);
        Vec3 dir = ray.getDirection();

        double a = dir.dot(dir);
        double b = 2.0 * oc.dot(dir);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = (b * b) - (4 * a * c);

        return discriminant > 0;
    }

    private Vec3 rayColor(Ray3 ray) {
        if (hitSphere(new Vec3(0, 0, -1), 0.5, ray)) {
            return new Vec3(1, 0, 0);
        }

        Vec3 unitDir = ray.getDirection().unit();
        double t = 0.5 * (unitDir.getY() + 1.0);

        Vec3 color = new Vec3(1.0, 1.0, 1.0).mul(1.0 - t);
        color = color.add(new Vec3(0.5, 0.7, 1.0).mul(t));

        return color;
    }

    private int toRGB(Vec3 color) {
        return toRGB(color.getX(), color.getY(), color.getZ());
    }

    private int toRGB(double r, double g, double b) {
        int ir = (int) (255.999 * r);
        int ig = (int) (255.999 * g);
        int ib = (int) (255.999 * b);

        return (ir << 16) | (ig << 8) | ib;
    }

}
