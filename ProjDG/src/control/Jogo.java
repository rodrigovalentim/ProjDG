package control;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import java.awt.event.*;
import javax.swing.JFrame;
import model.Pedra;
import service.Jogador;
import service.Peca;
import service.Tabuleiro;

public class Jogo extends JFrame {

    private static Jogador jogador1, jogador2;
    private Jogador[] jogadores;
    private Tabuleiro tabuleiro;
    private ArrayList<Pedra> pedrasCapturadas;

    public Jogo(Jogador jogador1, Jogador jogador2) {
        /*
         * Construindo parte grafica
         */
        super("Jogo de Damas");
        final JDesktopPane guiDamas = new JDesktopPane();
        getContentPane().add(guiDamas);

        /*
         * Escutador do click do botão close, que ao ser clicado, fecha o jogo.
         */
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        /*
         * Inicializando o jogo
         */
        tabuleiro = new Tabuleiro();
        jogadores = new Jogador[2]; //criando objeto jogadores
        jogadores[0] = jogador1; //atribindo valores ao jogador 1
        jogadores[0].setId(0); //Setando id do jogador
        jogadores[1] = jogador2; //atribindo valores ao jogador 2
        jogadores[1].setId(1); //Setando id do jogador

        distribuirPedras();
        posicionarPedras(jogador1);
        posicionarPedras(jogador2);
        getTabuleiro().mostra(guiDamas);
        /*
         * Inicializando Variaveis
         */
        pedrasCapturadas = new ArrayList<Pedra>();
        System.out.println("Jogador 1 " + jogadores[0].getNome() + " Jogador 2 " + jogadores[1].getNome());
        show();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando Damas");
        jogador1 = new Jogador("Rodrigo");
        jogador2 = new Jogador("PC");
        Jogo mainFrame = new Jogo(jogador1, jogador2);
        mainFrame.setSize(640, 480);
        mainFrame.setVisible(true);
    }

    public void distribuirPedras() {
        for (int i = 1; i <= 12; i++) {
            getJogadores()[0].addPedra(new Peca(i, Color.black));
        }
        for (int i = 13; i <= 24; i++) {
            getJogadores()[1].addPedra(new Peca(i, Color.white));
        }
    }

    public void posicionarPedras(Jogador jogador) {
        int qtdCasas = 0;
        int posicaoInic = -1;
        //setando posições dos jogadores
        if (jogador.getId() == 0) {
            posicaoInic = 0; //jogador 1 posicao superior
        } else if (jogador.getId() == 1) {
            posicaoInic = 40; //jogador 40 posicao inferior
        }
        for (int pecas = 0; pecas < 12; pecas++) {
            while (qtdCasas < 24) {
                if (getTabuleiro().getDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8),((posicaoInic + qtdCasas) % 8)).equals("#")) {
                    if (jogador.getId() == 0) {
                        getTabuleiro().setDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8),((posicaoInic + qtdCasas) % 8), "0");
                    } else if (jogador.getId() == 1) {
                        getTabuleiro().setDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8),((posicaoInic + qtdCasas) % 8), "1");
                    }
                    getTabuleiro().getCasas()[((posicaoInic + qtdCasas) / 8)][((posicaoInic + qtdCasas) % 8)].setPedra(getJogadores()[jogador.getId()].getPedras().get(pecas));
                    getTabuleiro().getCasas()[((posicaoInic + qtdCasas) / 8)][((posicaoInic + qtdCasas) % 8)].setForeground(getJogadores()[jogador.getId()].getPedras().get(pecas).getCor());
                }
                qtdCasas++;
            }
        }
    }

    /**
     * @return the tabuleiro
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * @param tabuleiro the tabuleiro to set
     */
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    /**
     * @return the jogadores
     */
    public Jogador[] getJogadores() {
        return jogadores;
    }

    /**
     * @param jogadores the jogadores to set
     */
    public void setJogadores(Jogador[] jogadores) {
        this.jogadores = jogadores;
    }
}
