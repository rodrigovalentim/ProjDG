/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Rodrigo
 */
public class toImageIcon {

    public ImageIcon toImageIcon(BufferedImage img) {
        ImageIcon icon = new ImageIcon(img.getScaledInstance(img.getWidth(), img.getHeight(), 10000));
        return icon;
    }
}
