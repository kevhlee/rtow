package trace;

import trace.render.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        int width = 256;
        int height = 256;

        Renderer renderer = new Renderer();

        RenderedImage renderedImage = renderer.render(width, height);

        try {
            ImageIO.write(renderedImage, "png", new File("render.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace(System.err);
        }
    }

}
