package trace.render;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;
import trace.hittable.Hittable;

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

    public RenderedImage render(Hittable world) {
        int width = camera.getWidth();
        int height = camera.getHeight();

        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            System.out.print(
                    "\rScanlines remaining: " + (height - y - 1) + " \b");

            for (int x = 0; x < width; x++) {
                Ray3 ray = camera.buildRay(x, y);

                Vec3 color = rayColor(ray, world);

                bufferedImage.setRGB(x, height - (y + 1), toRGB(color));
            }
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    private Vec3 rayColor(Ray3 ray, Hittable world) {
        HitRecord record = new HitRecord();

        if (world.hit(0, Double.MAX_VALUE, ray, record)) {
            return record.getNormal().add(new Vec3(1, 1, 1)).mul(0.5);
        }

        return backgroundColor(ray);
    }

    private Vec3 backgroundColor(Ray3 ray) {
        Vec3 unitDir = ray.getDirection().unit();
        double t = 0.5 * (unitDir.getY() + 1.0);

        Vec3 background = new Vec3(1.0, 1.0, 1.0).mul(1.0 - t);
        background = background.add(new Vec3(0.5, 0.7, 1.0).mul(t));

        return background;
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
