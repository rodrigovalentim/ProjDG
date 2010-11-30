package utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Rodrigo
 */
public class ImagemLoad {

    public BufferedImage imageLoader(String imgString) {
        /*
        O uso de thread se da deivo a rotina não saber qual qual thread estará rodando este código
        então se o classloader que carregou esta classe for diferente do classeloader que está rodando a thread
        ele não vai encontrar a imagem mesmo ela estando lá
         */
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
