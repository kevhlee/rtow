package trace;

import trace.geometry.Vec3;
import trace.hittable.Hittable;
import trace.hittable.HittableSet;
import trace.hittable.Sphere;
import trace.material.Dielectric;
import trace.material.Lambertian;
import trace.material.Material;
import trace.material.Metal;
import trace.render.Camera;
import trace.render.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class App {

    private static Hittable randomScene() {
        HittableSet world = new HittableSet();

        Material groundMat = new Lambertian(new Vec3(0.5, 0.5, 0.5));
        world.add(new Sphere(1000, new Vec3(0, -1000, 0), groundMat));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                Vec3 center = new Vec3(
                        a + 0.9 * Math.random(),
                        0.2,
                        b + 0.9 * Math.random());

                double chooseMat = Math.random();

                if (center.sub(new Vec3(4, 0.2, 0)).length() > 0.9) {
                    Material sphereMat;

                    if (chooseMat < 0.8) {
                        // Diffuse
                        sphereMat = new Lambertian(Vec3.rand(0, 0.5));
                    } else if (chooseMat < 0.95) {
                        // Metal
                        sphereMat =
                                new Metal(Vec3.rand(0.5, 1), Math.random());
                    } else {
                        // Glass
                        sphereMat = new Dielectric(1.5);
                    }

                    world.add(new Sphere(0.2, center, sphereMat));
                }
            }
        }

        Material mat1 = new Dielectric(1.5);
        Material mat2 = new Lambertian(new Vec3(0.4, 0.2, 0.1));
        Material mat3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);

        world.add(new Sphere(1.0, new Vec3(0, 1, 0), mat1));
        world.add(new Sphere(1.0, new Vec3(-4, 1, 0), mat2));
        world.add(new Sphere(1.0, new Vec3(4, 1, 0), mat3));

        return world;
    }

    public static void main(String[] args) {
        // Image
        int width = 1200;
        int height = 800;
        double aspectRatio = (double) width / height;

        // Camera
        Vec3 from = new Vec3(13, 2, 3);
        Vec3 at = new Vec3(0, 0, 0);
        Vec3 up = new Vec3(0, 1, 0);

        Camera camera = new Camera(from, at, up, 20, aspectRatio, 0.1, 10);

        // Render
        Renderer renderer = new Renderer(camera);

        renderer.setMaxRayDepth(50);
        renderer.setNumberOfSamples(500);

        RenderedImage renderedImage =
                renderer.render(randomScene(), width, height);

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

}
