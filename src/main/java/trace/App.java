package trace;

import trace.geometry.Vec3;
import trace.hittable.Hittable;
import trace.hittable.HittableSet;
import trace.hittable.Sphere;
import trace.render.Camera;
import trace.render.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class App {

    private static Hittable createWorld() {
        HittableSet hittableSet = new HittableSet();

        hittableSet.add(new Sphere(0.5, new Vec3(0, 0, -1)));
        hittableSet.add(new Sphere(100, new Vec3(0, -100.5, -1)));

        return hittableSet;
    }

    public static void main(String[] args) {
        int width = 400;
        int height = 225;

        Camera camera = new Camera(width, height);

        Renderer renderer = new Renderer(camera);
        
        RenderedImage renderedImage = renderer.render(createWorld());

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

}
