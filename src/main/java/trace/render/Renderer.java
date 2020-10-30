package trace.render;

import trace.geometry.Ray3;
import trace.geometry.Vec3;
import trace.hittable.HitRecord;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * A ray-tracing tracer.
 *
 * @author Kevin Lee
 */
public class Renderer {

    public static final double DEFAULT_T_MIN = 0.001;
    public static final double DEFAULT_T_MAX = Double.MAX_VALUE;
    public static final int DEFAULT_MAX_RAY_DEPTH = 50;
    public static final int DEFAULT_NUMBER_OF_SAMPLES = 100;

    private final Camera camera;
    private double tMin;
    private double tMax;
    private int maxRayDepth;
    private int numberOfSamples;

    public Renderer(Camera camera) {
        this.camera = camera;
        this.tMin = DEFAULT_T_MIN;
        this.tMax = DEFAULT_T_MAX;
        this.maxRayDepth = DEFAULT_MAX_RAY_DEPTH;
        this.numberOfSamples = DEFAULT_NUMBER_OF_SAMPLES;
    }

    public int getMaxRayDepth() {
        return maxRayDepth;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public double getMinT() {
        return tMin;
    }

    public double getMaxT() {
        return tMax;
    }

    public void setNumberOfSamples(int numberOfSamples) {
        this.numberOfSamples = numberOfSamples;
    }

    public void setMaxRayDepth(int maxRayDepth) {
        this.maxRayDepth = maxRayDepth;
    }

    public void setMinT(double tMin) {
        this.tMin = tMin;
    }

    public void setMaxT(double tMax) {
        this.tMax = tMax;
    }

    public RenderedImage render(Scene scene, int width, int height) {
        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        HitRecord record = new HitRecord();

        for (int y = 0; y < height; y++) {
            System.out.print(
                    "\rScanlines remaining: " + (height - y - 1) + " \b");

            for (int x = 0; x < width; x++) {
                Vec3 pixel = new Vec3(0, 0, 0);

                for (int s = 0; s < numberOfSamples; s++) {
                    double u = (x + Math.random()) / (width - 1);
                    double v = (y + Math.random()) / (height - 1);

                    Ray3 ray = camera.buildRay(u, v);

                    pixel = pixel.add(
                            rayColor(ray, scene, record, 0));
                }

                bufferedImage.setRGB(x, height - (y + 1), toRGB(pixel));
            }
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    private Vec3 rayColor(
            Ray3 ray, Scene scene, HitRecord record, int depth) {

        if (depth >= maxRayDepth) {
            return new Vec3(0, 0, 0);
        }

        if (scene.hit(tMin, tMax, ray, record)) {
            Vec3 attenuation = new Vec3(0, 0, 0);

            if (record.getMaterial().scatter(ray, record, attenuation)) {
                return attenuation.mul(rayColor(ray, scene, record, depth + 1));
            }

            return new Vec3(0, 0, 0);
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
        r = Math.sqrt(r / numberOfSamples);
        g = Math.sqrt(g / numberOfSamples);
        b = Math.sqrt(b / numberOfSamples);

        int ir = (int) (256 * clamp(r, 0.0, 0.999));
        int ig = (int) (256 * clamp(g, 0.0, 0.999));
        int ib = (int) (256 * clamp(b, 0.0, 0.999));

        return (ir << 16) | (ig << 8) | ib;
    }

    private double clamp(double a, double min, double max) {
        return Math.min(Math.max(a, min), max);
    }

}
