/* Projeto Academico de Construcao de um jogo em Java. O jogo escolhido foi o DAMA
 *
 * Faculdade Jorge Amado
 * Curso de Desenvolvimento de Software
 * Autores: Davi Sande, Rodrigo Valentim, Ueber Lima
 *
 */
package main;

import java.awt.*;
import javax.swing.JDesktopPane;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import service.Jogador;
import service.Peca;
import service.Placar;
import service.Tabuleiro;

public class Jogo extends JFrame {

    private static Jogador jogador1, jogador2;
    private Jogador[] jogadores;
    private Tabuleiro tabuleiro;
    private Placar placar;
    private ActionListener action;

    public Jogo(Jogador jogador1, Jogador jogador2) {
        /*
         * Construindo parte grafica
         */
        super("Faculdade Jorge Amado - Jogo de Damas - Davi Sande | Rodrigo Valentim | Ueber Lima");
        final JDesktopPane guiDamas = new JDesktopPane();
        getContentPane().add(guiDamas);
        setResizable(false); //impossibilitando o resize
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
        jogadores[1] = jogador2; //atribindo valores ao jogador 2
        placar = new Placar(jogador1, jogador2);
        distribuirPedras();
        posicionarPedras(jogador1);
        posicionarPedras(jogador2);
        getTabuleiro().mostra(guiDamas); //Exibe o tabuleiro na tela principal
        getPlacar().mostra(guiDamas); //Exibe o placar na tela principal
        /*
         * Inicializando Variaveis
         */
        action = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                getPlacar().atualizarPlacar();
            }
        };
        initialize();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando Damas");
        jogador1 = new Jogador("Player 1", 0, 0);
        jogador2 = new Jogador("Player 2", 1, 40);
        Jogo mainFrame = new Jogo(jogador1, jogador2);
        mainFrame.setSize(908, 700);
        mainFrame.setVisible(true);
    }

    /*
     * Metodo Distribuit Pedras e responsalvel por colocar na mao dos jogores
     * suas respectivas pedras
     */
    public void distribuirPedras() {
        for (int i = 1; i <= 12; i++) {
            getJogadores()[0].addPedra(new Peca(getJogadores()[0].getId(), i, Color.black));
        }
        for (int i = 13; i <= 24; i++) {
            getJogadores()[1].addPedra(new Peca(getJogadores()[1].getId(), i, Color.white));
        }
    }

    /*
     * Classe posicionar Pedras eh responavel por posiconar as pedras em seus lugares
     * usando como base uma matriz logica feita para isso.
     */
    public void posicionarPedras(Jogador jogador) {
        /*
         * jogador 1 inicia na parte superior do tabuleiro com as pedras pretas
         * jogador 2 inicia na parte inferior do tabuleiro com as pedras brancas
         */

        /*
         * distribuindo as 12 pecas
         */
        for (int pecas = 0; pecas < 12; pecas++) {
            /*
             * cada jogador tem 24 casas (12 utilizadas e 12 inutilizadas)
             */
            for (int qtdCasas = 0; qtdCasas < 24; qtdCasas++) {
                /*
                 * O calculo usado abaixo, eh a forma mais precisa de identificar a posicao testaMovimentos e y da matriz logica.
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
                 *
                 * Com base na informacao booleana da casa, vejo se ela eh possivel de movimento ou nao, ou seja, casa clara ou escura
                 * alem disso vejo se a casa atual ja esta ocupada para por uma outra pedra no local.
                 *
                 */
                if ((getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].isCasaPossivel())
                        && (getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].getPedra() == null)) {
                    /*
                     * setando as pecas que estao na mao do jogador na casa informada como possivel
                     */
                    getTabuleiro().getCasas()[((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) / 8)][((getJogadores()[jogador.getId()].getPosicaoInicial() + qtdCasas) % 8)].setPedra(getJogadores()[jogador.getId()].getPedras().get(pecas));
                    /*
                     * BREAK 
                     * O Break foi usado para quando a condicao for atendida, 
                     * sair do loop e ir para proxima casa, pegando o proximo ID da peca
                     */
                    break;
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

    public void initialize() {
        Timer t = new Timer(5000, action);
        t.start();
    }
}
