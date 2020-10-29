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

    public static final int DEFAULT_NUMBER_OF_SAMPLES = 100;

    private final Camera camera;
    private int numberOfSamples;

    public Renderer(Camera camera) {
        this.camera = camera;
        this.numberOfSamples = DEFAULT_NUMBER_OF_SAMPLES;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    public RenderedImage render(Hittable world, int width, int height) {
        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            System.out.print(
                    "\rScanlines remaining: " + (height - y - 1) + " \b");

            for (int x = 0; x < width; x++) {
                Vec3 pixel = new Vec3(0, 0, 0);

                for (int s = 0; s < numberOfSamples; s++) {
                    double u = (x + Math.random()) / (width - 1);
                    double v = (y + Math.random()) / (height - 1);

                    Ray3 ray = camera.buildRay(u, v);

                    pixel = pixel.add(rayColor(ray, world));
                }

                pixel = pixel.mul(1.0 / numberOfSamples);

                bufferedImage.setRGB(x, height - (y + 1), toRGB(pixel));
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
        int ir = (int) (256 * clamp(r, 0.0, 0.999));
        int ig = (int) (256 * clamp(g, 0.0, 0.999));
        int ib = (int) (256 * clamp(b, 0.0, 0.999));

        return (ir << 16) | (ig << 8) | ib;
    }

    private double clamp(double a, double min, double max) {
        return Math.min(Math.max(a, min), max);
    }

}
