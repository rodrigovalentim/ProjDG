package main;

import control.Jogo;
import gui.Splash;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import service.Jogador;

/**
 *
 * @author rodrigo.valentim
 */
public class Damas {

    private static Jogador jogador1, jogador2;
    private static Jogo mainFrame;

    /*
     * Startando processo
     */
    public static void main(String[] args) {
        //SplashWindow telaSplash = new SplashWindow();
        //telaSplash.open(1000);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Splash();

        System.out.println("Iniciando Damas");
        jogador1 = new Jogador("Player 1", 0, 0);
        jogador2 = new Jogador("Player 2", 1, 40);
        mainFrame = new Jogo(jogador1, jogador2);
        mainFrame.setSize(780, 540);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((dimension.width - mainFrame.getSize().width) / 2, (dimension.height - mainFrame.getSize().height) / 2);
        mainFrame.setVisible(true);
    }
}
