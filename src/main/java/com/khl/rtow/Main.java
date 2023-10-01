package com.khl.rtow;

import com.khl.rtow.hittable.Sphere;
import com.khl.rtow.material.Dielectric;
import com.khl.rtow.material.Lambertian;
import com.khl.rtow.material.Material;
import com.khl.rtow.material.Metal;
import com.khl.rtow.math.Vec;
import com.khl.rtow.render.Camera;
import com.khl.rtow.render.Renderer;
import com.khl.rtow.render.Scene;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        final var width = 600;
        final var height = 400;

        var lookFrom = new Vec(13, 2, 3);
        var lookAt = new Vec(0, 0, 0);
        var lookUp = new Vec(0, 1, 0);

        var camera = Camera.builder(lookFrom, lookAt, lookUp)
                .setAperture(0.1)
                .setAspectRatio((double) width / height)
                .setFieldOfView(20)
                .setFocusDistance(10)
                .build();

        var renderer = Renderer.builder(0.001, Double.MAX_VALUE)
                .setMaxRayDepth(30)
                .setNumberOfSamples(50)
                .build();

        var renderedImage = renderer.render(randomScene(), camera, width, height);

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

    private static Scene randomScene() {
        var scene = new Scene();

        // Generate foreground
        for (var a = -11; a < 11; a++) {
            for (var b = -11; b < 11; b++) {
                var x = a + 0.9 * Math.random();
                var y = 0.2;
                var z = b + 0.9 * Math.random();

                var center = new Vec(x, y, z);
                if (center.sub(new Vec(4, 0.2, 0)).length() > 0.9) {
                    scene.add(new Sphere(0.2, center, randomMaterial()));
                }
            }
        }

        // Generate background
        var mat1 = new Dielectric(1.5);
        var mat2 = new Lambertian(new Vec(0.4, 0.2, 0.1));
        var mat3 = new Metal(new Vec(0.7, 0.6, 0.5), 0.0);
        var matGround = new Lambertian(new Vec(0.5, 0.5, 0.5));

        scene.add(new Sphere(1.0, new Vec(0, 1, 0), mat1));
        scene.add(new Sphere(1.0, new Vec(-4, 1, 0), mat2));
        scene.add(new Sphere(1.0, new Vec(4, 1, 0), mat3));
        scene.add(new Sphere(1000, new Vec(0, -1000, 0), matGround));

        return scene;
    }

    private static Material randomMaterial() {
        var choose = Math.random();
        if (choose < 0.8) {
            return new Lambertian(Vec.rand(0, 0.5));
        } else if (choose < 0.95) {
            return new Metal(Vec.rand(0.5, 1), Math.random());
        } else {
            return new Dielectric(1.5);
        }
    }

}