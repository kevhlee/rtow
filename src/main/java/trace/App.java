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

    private static Hittable createWorld() {
        HittableSet world = new HittableSet();

        Material leftMat = new Dielectric(1.5);
        Material rightMat = new Metal(new Vec3(0.8, 0.6, 0.2), 1.0);
        Material groundMat = new Lambertian(new Vec3(0.8, 0.8, 0.0));
        Material centerMat = new Lambertian(new Vec3(0.1, 0.2, 0.5));

        world.add(new Sphere(100.0, new Vec3(0.0, -100.5, -1.0), groundMat));
        world.add(new Sphere(0.5, new Vec3(0.0, 0.0, -1.0), centerMat));
        world.add(new Sphere(0.5, new Vec3(-1.0, 0.0, -1.0), leftMat));
        world.add(new Sphere(-0.4, new Vec3(-1.0, 0.0, -1.0), leftMat));
        world.add(new Sphere(0.5, new Vec3(1.0, 0.0, -1.0), rightMat));

        return world;
    }

    public static void main(String[] args) {
        // Image
        int width = 400;
        int height = 256;
        double aspectRatio = (double) width / height;

        // Camera
        Vec3 from = new Vec3(3, 3, 2);
        Vec3 at = new Vec3(0, 0, -1);
        Vec3 up = new Vec3(0, 1, 0);
        double distToFocus = from.sub(at).length();

        Camera camera =
                new Camera(from, at, up, 20, aspectRatio, 2.0, distToFocus);

        // Render
        Renderer renderer = new Renderer(camera);
        RenderedImage renderedImage =
                renderer.render(createWorld(), width, height);

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

}
