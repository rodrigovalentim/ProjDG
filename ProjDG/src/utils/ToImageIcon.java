package utils;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ToImageIcon {

    public ImageIcon toImageIcon(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img.getScaledInstance(img.getWidth(), img.getHeight(), 10000));
        return icon;
    }
}
