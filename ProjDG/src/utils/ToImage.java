package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ToImage {

    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
}
