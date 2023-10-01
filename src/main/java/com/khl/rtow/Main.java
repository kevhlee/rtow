package com.khl.rtow;

import com.khl.rtow.hittable.Sphere;
import com.khl.rtow.material.Dielectric;
import com.khl.rtow.material.Lambertian;
import com.khl.rtow.material.Material;
import com.khl.rtow.material.Metal;
import com.khl.rtow.math.Vec3;
import com.khl.rtow.render.Camera;
import com.khl.rtow.render.Renderer;
import com.khl.rtow.render.Scene;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        final int width = 600;
        final int height = 400;

        Vec3 lookFrom = new Vec3(13, 2, 3);
        Vec3 lookAt = new Vec3(0, 0, 0);
        Vec3 lookUp = new Vec3(0, 1, 0);

        Camera camera = Camera.builder(lookFrom, lookAt, lookUp)
                .setAperture(0.1)
                .setAspectRatio((double) width / height)
                .setFieldOfView(20)
                .setFocusDistance(10)
                .build();

        Renderer renderer = Renderer.builder(0.001, Double.MAX_VALUE)
                .setMaxRayDepth(30)
                .setNumberOfSamples(50)
                .build();

        RenderedImage renderedImage =
                renderer.render(randomScene(), camera, width, height);

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
                    scene.add(new Sphere(0.2, center, randomMaterial()));
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

    private static Material randomMaterial() {
        double choose = Math.random();
        if (choose < 0.8) {
            return new Lambertian(Vec3.rand(0, 0.5));
        } else if (choose < 0.95) {
            return new Metal(Vec3.rand(0.5, 1), Math.random());
        } else {
            return new Dielectric(1.5);
        }
    }

}