package utils;

import control.Jogo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import service.Casa;

/**
 *
 * @author Rodrigo
 */
public class imageLoader {

    private BufferedImage imagem;

    public BufferedImage imageLoader(String imgString) {
        try {
            System.out.println(getClass().getResource("/imagem/img.png").toString());

            File img = new File(getClass().getResource("/imagem/img.png").toString());
            imagem = ImageIO.read(img);
        } catch (IOException ex) {
            Logger.getLogger(Casa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imagem;
    }
}
