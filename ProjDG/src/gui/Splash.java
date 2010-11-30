/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.ImageIcon;
import utils.ImagemLoad;
import utils.ToImageIcon;

/**
 *
 * @author rodrigo.valentim
 */
public class Splash {

    SplashScreen screen;

    public Splash() {
        // inicializa screen
        splashScreenInit();
        // do something here to simulate the program doing something that
        // is time consuming
        for (int i = 0; i <= 100; i++) {
            for (long j = 0; j < 30; ++j) {
                String poop = " " + (j + i);
            }
            screen.setProgress("Carregando " + i + "%", i);  // mensagem do progressbar
        }
        splashScreenDestruct();
    }

    private void splashScreenDestruct() {
        screen.setScreenVisible(false);
    }

    private void splashScreenInit() {
        ImageIcon myImage = new ToImageIcon().toImageIcon(new ImagemLoad().imageLoader("imagem/tabuleiro_damas.png"));
        screen = new SplashScreen(myImage);
        screen.setLocationRelativeTo(null);
        screen.setProgressMax(100);
        screen.setScreenVisible(true);
    }
}
