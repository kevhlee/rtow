package com.khl.rtow.render;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * A ray-tracing tracer.
 *
 * @author Kevin Lee
 */
public class Renderer {

    private final double tMin;
    private final double tMax;

    private final int maxRayDepth;
    private final int numberOfSamples;

    private Renderer(Builder builder) {
        this.tMin = builder.tMin;
        this.tMax = builder.tMax;
        this.maxRayDepth = builder.maxRayDepth;
        this.numberOfSamples = builder.numberOfSamples;
    }

    public static Builder builder(double tMin, double tMax) {
        return new Builder(tMin, tMax);
    }

    public RenderedImage render(Scene scene, Camera camera, int width, int height) {
        var bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var record = new HitRecord();

        for (var y = 0; y < height; y++) {
            System.out.print("\rScanlines remaining: " + (height - y - 1) + " \b");

            for (var x = 0; x < width; x++) {
                var pixel = new Vec(0, 0, 0);

                for (int s = 0; s < numberOfSamples; s++) {
                    double u = (x + Math.random()) / (width - 1);
                    double v = (y + Math.random()) / (height - 1);

                    pixel.addInPlace(trace(camera.generateRay(u, v), scene, record, 0));
                }

                bufferedImage.setRGB(x, height - (y + 1), toRGB(pixel));
            }
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    private Vec trace(Ray ray, Scene scene, HitRecord record, int depth) {
        if (depth >= maxRayDepth) {
            return new Vec(0, 0, 0);
        }

        if (scene.hit(tMin, tMax, ray, record)) {
            var attenuation = new Vec(0, 0, 0);
            var material = record.getMaterial();

            if (material.scatter(ray, record, attenuation)) {
                return attenuation.mul(trace(ray, scene, record, depth + 1));
            }

            return new Vec(0, 0, 0);
        }

        return backgroundColor(ray);
    }

    private Vec backgroundColor(Ray ray) {
        var unitDir = ray.getDirection().unit();
        var t = 0.5 * (unitDir.getY() + 1.0);

        return new Vec(1.0, 1.0, 1.0)
                .mul(1.0 - t)
                .add(new Vec(0.5, 0.7, 1.0).mul(t));
    }

    private int toRGB(Vec color) {
        return toRGB(color.getX(), color.getY(), color.getZ());
    }

    private int toRGB(double r, double g, double b) {
        r = Math.sqrt(r / numberOfSamples);
        g = Math.sqrt(g / numberOfSamples);
        b = Math.sqrt(b / numberOfSamples);

        var ir = (int) (256 * clamp(r, 0.0, 0.999));
        var ig = (int) (256 * clamp(g, 0.0, 0.999));
        var ib = (int) (256 * clamp(b, 0.0, 0.999));

        return (ir << 16) | (ig << 8) | ib;
    }

    private double clamp(double a, double min, double max) {
        return Math.min(Math.max(a, min), max);
    }

    public static class Builder {

        private final double tMax;
        private final double tMin;

        private int maxRayDepth;
        private int numberOfSamples;

        private Builder(double tMin, double tMax) {
            this.tMax = tMax;
            this.tMin = tMin;
        }

        public Builder setMaxRayDepth(int maxRayDepth) {
            this.maxRayDepth = maxRayDepth;
            return this;
        }

        public Builder setNumberOfSamples(int numberOfSamples) {
            this.numberOfSamples = numberOfSamples;
            return this;
        }

        public Renderer build() {
            return new Renderer(this);
        }

    }

}