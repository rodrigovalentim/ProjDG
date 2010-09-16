/* Projeto Academico de Construcao de um jogo em Java. O jogo escolhido foi o DAMA
 *
 * Faculdade Jorge Amado
 * Curso de Desenvolvimento de Software
 * Autores: Davi Sande, Rodrigo Valentim, Ueber Lima
 *
 */
package control;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import java.awt.event.*;
import javax.swing.JFrame;
import model.Pedra;
import service.Jogador;
import service.Peca;
import service.Placar;
import service.Tabuleiro;

public class Jogo extends JFrame {

    private static Jogador jogador1, jogador2;
    private Jogador[] jogadores;
    private Tabuleiro tabuleiro;
    private Placar placar;
    private ArrayList<Pedra> pedrasCapturadas;

    public Jogo(Jogador jogador1, Jogador jogador2) {
        /*
         * Construindo parte grafica
         */
        super("Faculdade Jorge Amado - Jogo de Damas - Davi | Rodrigo | Ueber");
        final JDesktopPane guiDamas = new JDesktopPane();
        getContentPane().add(guiDamas);

        /*
         * Escutador do click do botao close para fechar o jogo.
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
        jogadores[0].setId(0); //Setando id do jogador 1
        jogadores[1] = jogador2; //atribindo valores ao jogador 2
        jogadores[1].setId(1); //Setando id do jogador 2
        placar = new Placar(jogador1, jogador2);
        distribuirPedras();
        posicionarPedras(jogador1);
        posicionarPedras(jogador2);
        getTabuleiro().mostra(guiDamas);
        getPlacar().mostra(guiDamas);
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
        mainFrame.setSize(610, 432);
        mainFrame.setVisible(true);
    }

    /*
     * Metodo Distribuit Pedras e responsalvel por colocar na mao dos jogores
     * suas respectivas pedras
     */
    public void distribuirPedras() {
        for (int i = 1; i <= 12; i++) {
            getJogadores()[0].addPedra(new Peca(i, Color.black));
        }
        for (int i = 13; i <= 24; i++) {
            getJogadores()[1].addPedra(new Peca(i, Color.white));
        }
    }

    /*
     * Classe posicionar Pedras eh responavel por posiconar as pedras em seus lugares
     * usando como base uma matriz logica feita para isso.
     */
    public void posicionarPedras(Jogador jogador) {
        int posicaoInic = -1;

        /*
         * jogador 1 inicia na parte superior do tabuleiro com as pedras pretas
         * jogador 2 inicia na parte inferior do tabuleiro com as pedras brancas
         */

        if (jogador.getId() == 0) {
            /*
             * Iniciando posicao do jogador um na parte superior
             */
            posicaoInic = 0;
        } else if (jogador.getId() == 1) {
            /*
             * Iniciando posicao do jogador um na parte inferior
             */
            posicaoInic = 40;
        }
        /*
         * distribuindo as 12 pecas
         */
        for (int pecas = 0; pecas < 12; pecas++) {
            /*
             * cada jogador tem 24 casas (12 utilizadas e 12 inutilizadas)
             */
            for (int qtdCasas = 0; qtdCasas < 24; qtdCasas++) {
                /*
                 * O calculo usado abaixo, eh a forma mais precisa de identificar a posicao x e y da matriz logica.
                 *
                 * Se tenho posicao 0 + qtdCasas que vai de 0 ate ate 23, entao, fazendo o calculo usando DIV e MOD, obtenho exatamente
                 * a posicao X e Y
                 * [0,0][0,1][0,2][0,3][0,4][0,5][0,6][0,7]
                 * [1,0][1,1][1,2][1,3][1,4][1,5][1,6][1,7]
                 * [2,0][2,1][2,2][2,3][2,4][2,5][2,6][2,7]
                 * [3,0][3,1][3,2][3,3][3,4][3,5][3,6][3,7]
                 * [4,0][4,1][4,2][4,3][4,4][4,5][4,6][4,7]
                 * [5,0][5,1][5,2][5,3][5,4][5,5][5,6][5,7]
                 * [6,0][6,1][6,2][6,3][6,4][6,5][6,6][6,7]
                 * [7,0][7,1][7,2][7,3][7,4][7,5][7,6][7,7]
                 */
                if (getTabuleiro().getDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8), ((posicaoInic + qtdCasas) % 8)).equals("#")) {

                    /*
                     * indicando na matriz logica onde cada jogador esta
                     * Legenda:
                     * Casa indicada com 0, jogador 1.
                     * Casa indicada com 1, jogador 2.
                     */

                    if (jogador.getId() == 0) {
                        getTabuleiro().setDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8), ((posicaoInic + qtdCasas) % 8), "0");
                    } else if (jogador.getId() == 1) {
                        getTabuleiro().setDesenhaTabuleiroMatriz(((posicaoInic + qtdCasas) / 8), ((posicaoInic + qtdCasas) % 8), "1");
                    }
                    /*
                     * Setando a peca grafica na casa logica
                     * pintando a area da peca de acordo com a cor definida na pedra
                     */
                    getTabuleiro().getCasas()[((posicaoInic + qtdCasas) / 8)][((posicaoInic + qtdCasas) % 8)].setPedra(getJogadores()[jogador.getId()].getPedras().get(pecas));
                    getTabuleiro().getCasas()[((posicaoInic + qtdCasas) / 8)][((posicaoInic + qtdCasas) % 8)].setForeground(getJogadores()[jogador.getId()].getPedras().get(pecas).getCor());
                }
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

    /**
     * @return the placar
     */
    public Placar getPlacar() {
        return placar;
    }

    /**
     * @param placar the placar to set
     */
    public void setPlacar(Placar placar) {
        this.placar = placar;
    }
}
