package com.khl.trace;

import com.khl.trace.geometry.Vec3;
import com.khl.trace.hittable.Sphere;
import com.khl.trace.material.Dielectric;
import com.khl.trace.material.Lambertian;
import com.khl.trace.material.Material;
import com.khl.trace.material.Metal;
import com.khl.trace.render.Camera;
import com.khl.trace.render.Renderer;
import com.khl.trace.render.Scene;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Renderer
        int width = 600;
        int height = 400;
        int maxRayDepth = 30;
        int numberOfSamples = 50;
        double tMin = 0.001;
        double tMax = Double.MAX_VALUE;

        // Camera
        double aperture = 0.1;
        double aspectRatio = (double) width / height;
        double fieldOfView = 20;
        double focusDistance = 10;

        Vec3 lookFrom = new Vec3(13, 2, 3);
        Vec3 lookAt = new Vec3(0, 0, 0);
        Vec3 lookUp = new Vec3(0, 1, 0);

        Camera camera = new Camera.Builder(lookFrom, lookAt, lookUp).setAperture(aperture)
                .setAspectRatio(aspectRatio)
                .setFieldOfView(fieldOfView)
                .setFocusDistance(focusDistance)
                .build();

        Renderer renderer = new Renderer.Builder(tMin, tMax).setMaxRayDepth(maxRayDepth)
                .setNumberOfSamples(numberOfSamples)
                .build();

        RenderedImage renderedImage = renderer.render(randomScene(), camera, width, height);

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

    private static Scene randomScene() {
        Scene scene = new Scene();

        // Generate foreground
        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double x = a + 0.9 * Math.random();
                double y = 0.2;
                double z = b + 0.9 * Math.random();

                Vec3 center = new Vec3(x, y, z);

                if (center.sub(new Vec3(4, 0.2, 0)).length() > 0.9) {
                    Material sphereMat;

                    double matChoose = Math.random();

                    if (matChoose < 0.8) {
                        // Diffuse
                        sphereMat = new Lambertian(Vec3.rand(0, 0.5));
                    } else if (matChoose < 0.95) {
                        // Metal
                        sphereMat = new Metal(Vec3.rand(0.5, 1), Math.random());
                    } else {
                        // Glass
                        sphereMat = new Dielectric(1.5);
                    }

                    scene.add(new Sphere(0.2, center, sphereMat));
                }
            }
        }

        // Generate background
        Material mat1 = new Dielectric(1.5);
        Material mat2 = new Lambertian(new Vec3(0.4, 0.2, 0.1));
        Material mat3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);
        Material matGround = new Lambertian(new Vec3(0.5, 0.5, 0.5));

        scene.add(new Sphere(1.0, new Vec3(0, 1, 0), mat1));
        scene.add(new Sphere(1.0, new Vec3(-4, 1, 0), mat2));
        scene.add(new Sphere(1.0, new Vec3(4, 1, 0), mat3));
        scene.add(new Sphere(1000, new Vec3(0, -1000, 0), matGround));

        return scene;
    }

}
