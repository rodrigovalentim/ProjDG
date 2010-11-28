package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Rodrigo
 */
public class ImageLoader {

    public BufferedImage imageLoader(String imgString) {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();

        BufferedImage imagem = null;
        try {
            URL imgUrl = ccl.getResource(imgString);
            if (imgUrl == null) {
                throw new IllegalArgumentException("imgString='" + imgString + "' is not a valid image!");
            }

            imagem = ImageIO.read(imgUrl);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return imagem;
    }
}
