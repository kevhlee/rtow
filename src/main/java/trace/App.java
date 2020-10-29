package trace;

import trace.render.Camera;
import trace.render.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        int width = 400;
        int height = 225;

        Camera camera = new Camera(width, height);
        Renderer renderer = new Renderer(camera);

        RenderedImage renderedImage = renderer.render();

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

}
