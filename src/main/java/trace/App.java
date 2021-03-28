package trace;

import trace.geometry.Vec3;
import trace.hittable.Sphere;
import trace.material.Dielectric;
import trace.material.Lambertian;
import trace.material.Material;
import trace.material.Metal;
import trace.render.*;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class App {

    private static Scene randomScene() {
        Scene scene = new Scene();

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                Vec3 center = new Vec3(
                        a + 0.9 * Math.random(),
                        0.2,
                        b + 0.9 * Math.random());

                double matChoose = Math.random();

                if (center.sub(new Vec3(4, 0.2, 0)).length() > 0.9) {
                    Material sphereMat;

                    if (matChoose < 0.8) {
                        // Diffuse
                        sphereMat = new Lambertian(Vec3.rand(0, 0.5));
                    } else if (matChoose < 0.95) {
                        // Metal
                        sphereMat =
                                new Metal(Vec3.rand(0.5, 1), Math.random());
                    } else {
                        // Glass
                        sphereMat = new Dielectric(1.5);
                    }

                    scene.add(new Sphere(0.2, center, sphereMat));
                }
            }
        }

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

    public static void main(String[] args) {
        int width = 600;
        int height = 400;

        Camera camera = new Camera.Builder()
                .setLookFrom(new Vec3(13, 2, 3))
                .setLookAt(new Vec3(0, 0, 0))
                .setLookUp(new Vec3(0, 1, 0))
                .setAperture(0.1)
                .setAspectRatio((double) width / height)
                .setFieldOfView(20)
                .setFocusDistance(10)
                .build();

        Renderer renderer = new Renderer.Builder()
                .setMinT(0.001)
                .setMaxT(Double.MAX_VALUE)
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

}
