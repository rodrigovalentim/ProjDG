package service;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class Placar extends JInternalFrame {

    private Jogador jogador1;
    private Jogador jogador2;

    public Placar(Jogador jogador1, Jogador jogador2){
        setJogador1(jogador1);
        setJogador2(jogador2);

        setBounds(402, 0, 200, 400);
    }
    
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

    public void atualizarPlacar(){
        System.out.println(jogador1.getNome() + " = " + jogador1.getPontos() + "\n" + jogador2.getNome() + " = " + jogador2.getPontos());
    }
}
