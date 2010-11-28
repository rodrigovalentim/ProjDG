package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Rodrigo
 */
public class ImageLoader {

    public BufferedImage imageLoader(String imgString) {
        /*
        O uso de thread se da deivo a rotina n�o saber qual qual thread estar� rodando este c�digo
        ent�o se o classloader que carregou esta classe for diferente do classeloader que est� rodando a thread
        ele n�o vai encontrar a imagem mesmo ela estando l�
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
