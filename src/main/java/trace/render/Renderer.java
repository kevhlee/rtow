package trace.render;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

/**
 * A ray-tracing tracer.
 *
 * @author Kevin Lee
 */
public class Renderer {

    public RenderedImage render(int width, int height) {
        BufferedImage bufferedImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            System.out.print(
                    "\rScanlines remaining: " + (height - y - 1) + " \b");

            for (int x = 0; x < width; x++) {
                double r = (double) x / (width - 1);
                double g = (double) y / (height - 1);
                double b = 0.25;

                bufferedImage.setRGB(x, height - (y + 1), toRGB(r, g, b));
            }
        }

        System.out.println("\nDone.");

        return bufferedImage;
    }

    private int toRGB(double r, double g, double b) {
        int ir = (int) (255.999 * r);
        int ig = (int) (255.999 * g);
        int ib = (int) (255.999 * b);

        return (ir << 16) | (ig << 8) | ib;
    }

}
