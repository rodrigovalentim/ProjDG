package main;

import control.Jogo;
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
        System.out.println("Iniciando Damas");
        jogador1 = new Jogador("Player 1", 0, 0);
        jogador2 = new Jogador("Player 2", 1, 40);
        mainFrame = new Jogo(jogador1, jogador2);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
