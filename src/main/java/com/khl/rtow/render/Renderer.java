package com.khl.rtow.render;

import com.khl.rtow.hittable.HitRecord;
import com.khl.rtow.math.Ray;
import com.khl.rtow.math.Vec;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * A ray-tracing tracer.
 *
 * @author Kevin Lee
 */
public class Renderer {

    public static final int DEFAULT_MAX_RAY_DEPTH = 30;
    public static final int DEFAULT_NUMBER_OF_SAMPLES = 50;
    public static final int DEFAULT_NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    private int maxRayDepth;
    private int numberOfSamples;
    private int numberOfThreads;
    private final double tMin;
    private final double tMax;

    public Renderer(double tMin, double tMax) {
        this(tMin, tMax, DEFAULT_MAX_RAY_DEPTH, DEFAULT_NUMBER_OF_SAMPLES);
    }

    public Renderer(double tMin, double tMax, int maxRayDepth, int numberOfSamples) {
        this(tMin, tMax, maxRayDepth, numberOfSamples, DEFAULT_NUMBER_OF_THREADS);
    }

    public Renderer(double tMin, double tMax, int maxRayDepth, int numberOfSamples, int numberOfThreads) {
        this.tMin = tMin;
        this.tMax = tMax;
        this.maxRayDepth = maxRayDepth;
        this.numberOfSamples = numberOfSamples;
        this.numberOfThreads = numberOfThreads;
    }

    public int getMaxRayDepth() {
        return maxRayDepth;
    }

    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public RenderedImage render(Scene scene, Camera camera, int width, int height)
            throws ExecutionException, InterruptedException {

        camera.initialize(width, height);

        printConfiguration(width, height, camera);

        var executor = Executors.newWorkStealingPool(numberOfThreads);
        var futures = new ArrayList<Future<Pixel>>();

        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                final var x = j;
                final var y = i;

                var future = executor.submit(() -> {
                    var result = new Vec(0, 0, 0);
                    var record = new HitRecord();

                    for (int s = 0; s < numberOfSamples; s++) {
                        var u = (x + Math.random()) / (width - 1);
                        var v = (y + Math.random()) / (height - 1);

                        result.addInPlace(trace(camera.generateRay(u, v), scene, record, 0));
                    }

                    return new Pixel(x, height - (y + 1), toRGB(result));
                });

                futures.add(future);
            }
        }

        var count = 0;
        var total = width * height;
        var bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        executor.shutdown();

        for (var future : futures) {
            var pixel = future.get();
            bufferedImage.setRGB(pixel.x, pixel.y, pixel.rgb);
            count++;
            System.out.print("\rPercentage of image rendered: " + (100 * count / total) + "% \b");
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    public Renderer setMaxRayDepth(int maxRayDepth) throws IllegalArgumentException {
        if (maxRayDepth <= 0) {
            throw new IllegalArgumentException("Max ray depth must be positive");
        }

        this.maxRayDepth = maxRayDepth;
        return this;
    }

    public Renderer setNumberOfSamples(int numberOfSamples) {
        if (numberOfSamples <= 0) {
            throw new IllegalArgumentException("Number of samples must be positive");
        }

        this.numberOfSamples = numberOfSamples;
        return this;
    }

    public Renderer setNumberOfThreads(int numberOfThreads) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of rendering threads must be positive");
        }

        this.numberOfThreads = numberOfThreads;
        return this;
    }

    private Vec backgroundColor(Ray ray) {
        var unitDir = ray.getDirection().unit();
        var t = 0.5 * (unitDir.getY() + 1.0);

        return new Vec(1.0, 1.0, 1.0)
                .mul(1.0 - t)
                .add(new Vec(0.5, 0.7, 1.0).mul(t));
    }

    private double clamp(double a, double min, double max) {
        return Math.min(Math.max(a, min), max);
    }

    private void printConfiguration(int width, int height, Camera camera) {
        System.out.println("### Camera configuration ###");
        System.out.println("Origin: " + camera.getOrigin());
        System.out.println("Aperture: " + camera.getAperture());
        System.out.println("Field-of-view: " + camera.getFieldOfView());
        System.out.println("Focus distance: " + camera.getFocusDistance());
        System.out.println();

        System.out.println("### Renderer configuration ###");
        System.out.println("Image width: " + width);
        System.out.println("Image height: " + height);
        System.out.println("Max ray depth: " + getMaxRayDepth());
        System.out.println("Number of samples: " + getNumberOfSamples());
        System.out.println("Number of threads: " + getNumberOfThreads());
        System.out.println();
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

    private record Pixel(int x, int y, int rgb) {
    }

}