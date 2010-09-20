package control;

import main.Jogo;
import service.Jogador;

/**
 *
 * @author rodrigo.valentim
 */
public class Damas {

    private static Jogador jogador1, jogador2;

    /*
     * Startando processo
     */
    public static void main(String[] args) {
        System.out.println("Iniciando Damas");
        jogador1 = new Jogador("Player 1", 0, 0);
        jogador2 = new Jogador("Player 2", 1, 40);
        Jogo mainFrame = new Jogo(jogador1, jogador2);
        mainFrame.setSize(908, 700);
        mainFrame.setVisible(true);
    }
}
