/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import estruturas.Pilha;
import exception.PilhaVaziaException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import utils.ImagemLoad;
import utils.ToImageIcon;

/**
 *
 * @author rodrigo.valentim
 */
public class Splash {

    SplashScreen screen;
    private String x;

    public Splash() {
        // inicializa screen
        splashScreenInit();
        Pilha pilha = new Pilha();
        for (int i = 100; i >= 0; i--) {
            pilha.empilhar(i);
        }
        // do something here to simulate the program doing something that
        // is time consuming
        for (int i = 0; i <= 100; i++) {
            for (long j = 0; j < 300000; ++j) {
                String poop = " " + (j + i);
            }
            try {
                x = (pilha.desempilhar().toString());
            } catch (PilhaVaziaException ex) {
                Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
            }
            screen.setProgress("Carregando " + x + "%", i); // mensagem do progressbar
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
