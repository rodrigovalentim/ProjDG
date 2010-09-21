package service;

import java.awt.Component;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Placar extends JInternalFrame {

    private Jogador jogador1;
    private Jogador jogador2;
    private JTextArea resultado;

    public Placar(Jogador jogador1, Jogador jogador2) {
        setJogador1(jogador1);
        setJogador2(jogador2);
        setBounds(702, 0, 200, 400);
        resultado = new JTextArea();
        this.getContentPane().add(resultado);
        atualizarPlacar();
        setTitle("Placar");
    }

    /*
     * Metodo mostra torna possivel exibir este objeto grafico no frame principal.
     */
    public void mostra(JDesktopPane main) {
        main.add(this);
        setOpaque(true);
        show();
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    private void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    private void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public void verificaVencedor(Component parent) {
        if (jogador1.getPontos() == 12) {
            JOptionPane.showMessageDialog(parent, "Parabens ao jogador " + getJogador1().getNome() + "!!\n" + "Comeu Todas as Pecas do jogador " + getJogador2().getNome());
        }
        if (jogador2.getPontos() == 12) {
            JOptionPane.showMessageDialog(parent, "Parabens ao jogador " + getJogador2().getNome() + "!!\n" + "Comeu Todas as Pecas do jogador " + getJogador1().getNome());
        }
        /*( jogo.tabuleiro,
        "DamasJ \n\n Jogo de Damas implementado para a disciplina de Prog V\n" +
        "- Profesor Rolf F. Molz\nDiscentes:\n  => Eduardo Shoedles\n  => Marcelo Ivan Martin",
        "Sobre", JOptionPane.INFORMATION_MESSAGE);*/
    }

    public void atualizarPlacar() {
        resultado.setText(jogador1.getNome() + " = " + jogador1.getPontos() + "\n" + jogador2.getNome() + " = " + jogador2.getPontos());
    }
}
