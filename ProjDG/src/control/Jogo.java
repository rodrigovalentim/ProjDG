/* Projeto Academico de Construcao de um jogo em Java. O jogo escolhido foi o DAMA
 *
 * Faculdade Jorge Amado
 * Curso de Desenvolvimento de Software
 * Autores: Davi Sande, Rodrigo Valentim, Ueber Lima
 *
 */
package control;

import java.awt.*;
import javax.swing.JDesktopPane;
import java.awt.event.*;
import javax.swing.JFrame;
import service.Jogador;
import service.Peca;
import service.Placar;
import service.Tabuleiro;

public class Jogo extends JFrame {

    private Jogador[] jogadores;
    private Tabuleiro tabuleiro;
    private Placar placar;
    private static final int valAjusteClick = 30;
    private boolean novaJogada;
    private int jogadorDaVez;
    private int oldX;
    private int oldY;

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
         * Inicializando Variaveis
         */
        this.setOldX(-1);
        this.setOldY(-1);
        setJogadorDaVez(0); //Jogador da Vez - Inicia com o jogador 0
        jogadores = new Jogador[2];
        setJogadores(jogador1.getId(), jogador1);
        setJogadores(jogador2.getId(), jogador2);
        tabuleiro = new Tabuleiro();
        placar = new Placar(getJogadores()[jogador1.getId()], getJogadores()[jogador2.getId()]);
        distribuirPedras();
        posicionarPedras(getJogadores()[jogador1.getId()]);
        posicionarPedras(getJogadores()[jogador2.getId()]);
        getTabuleiro().mostra(guiDamas); //Exibe o tabuleiro na tela principal
        getPlacar().mostra(guiDamas); //Exibe o placar na tela principal
        /*
         * Inicializando Variaveis
         */
        getTabuleiro().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent mouseEvent) {
                capturaClicks(mouseEvent);
            }
        });
    }

    public void capturaClicks(MouseEvent e) {
        /*
         * Evento que dispara quando o botao do mouse e solto.
         *
         * super.getWidth serve para pegar a largura do componente
         *
         * super.getHeight serve para pegar a altura do componente
         *
         * A divisao por 8 e necessaria por que temos 8 casas
         *
         * Y esta recebendo testaMovimentos e X esta recebendo Y por que o mouseEvent interte isso, pra nos ajudar, claro.
         * Atraves destes testes, leitura de documentacoes
         * http://download.oracle.com/javase/1.4.2/docs/api/java/awt/event/MouseEvent.html
         * indentificamos que o MouseEvent retorna X como sendo linha e o Y como sendo coluna.
         * Invertendo as posicoes, tudo se resolveu.
         *
         *
         * O valor de ajuste igual a 20, quando subtraido da linha, temos uma maior precisao
         * no calculo nos possibilitando clicar bem proximo da borda da casa sem que ele pegue
         * a casa errada. Sem esse valor de ajuste, ao nos aproximarmos das bordas, ele pegava a casa vizinha.
         *
         */
        int y = (e.getX()) / (getTabuleiro().getWidth() / 8);
        int x = (e.getY() - valAjusteClick) / (getTabuleiro().getHeight() / 8);

        /*
         * Variavel abaixo usada para configurar a reanalise dos movimentos
         */
        boolean reAnaliseMovimento = false;
        /*
         * Metodo set da rotina que configura novaJogada
         */
        setNovaJogada(false);
        /*
         * Rotina abaixo remover que foram "comidas" pelo adversario
         */
        if (getTabuleiro().getCasas()[x][y].isMovimentoPossivel()) {
            /*
             * Calculo usado para saber se o movimento realizado foi de mais de uma casa
             * Caso posivito, entra na rotina de analise de possiveis pecas comidas
             */
            if (Math.abs(getOldY() - y) >= 2) {
                int l = getOldX(), c = getOldY();
                if (x > l && y > c) {
                    while (getTabuleiro().getCasas()[l++][c++].isCasaPossivel()
                            && l < x
                            && c < y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x > l && y < c) {
                    while (getTabuleiro().getCasas()[l++][c--].isCasaPossivel()
                            && l < x
                            && c > y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y > c) {
                    while (getTabuleiro().getCasas()[l--][c++].isCasaPossivel()
                            && l > x
                            && c < y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y < c) {
                    while (getTabuleiro().getCasas()[l--][c--].isCasaPossivel()
                            && l > x
                            && c > y) {
                        retiraPeca(l, c);
                    }
                }
                /*
                 * Rotina de movimentacao da peca, da origem pro destino
                 */
                move(getOldX(), getOldY(), x, y);
                setOldX(x);
                setOldY(y);
                reAnaliseMovimento = true;
                /*
                 * Re analisa os movimentos para saber se e possivel comer mais pedras antes de parar
                 */
                analistaTipoMovimento(x, y, reAnaliseMovimento);
            } else {
                /*
                 * Simples rotina de movimento, sem acao de comer
                 */
                move(getOldX(), getOldY(), x, y);
            }
            /*
             * Controla vez do jgoador
             */
            if (!isNovaJogada()) {
                mudarJogadorVez();
                reAnaliseMovimento = false;
            }
        }
        /*
         * Limpa casas selecionads
         */
        hideCasaSelecionada();
        /*
         * limpa Movimentos selecionados
         */
        hideMovimentosPossiveis();

        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()) {
            /*
             * seta como selecionada a casa, pintando sua borda
             */
            getTabuleiro().getCasas()[x][y].setCasaSelecionada(true, Color.black);
            setOldX(x);
            setOldY(y);
            analistaTipoMovimento(getOldX(), getOldY(), reAnaliseMovimento);
        }
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

    private void setDama(int linha, int coluna) {
        getTabuleiro().promovePedra(getJogadorDaVez(), linha, coluna);
    }

    /*
     * Metodo MOVE remove a pedra da casa de origem e coloca o mesmo na casa de destino.
     * Caso a casa de destino seja a primeira linha superior (posicao 0 do array) ou inferior (posicao 7 do array)
     * O metodo promovePedra eh chamado.
     */
    private void move(int linhaCasaOrigem, int ColunaCasaOrigem, int LinhaCasaDestino, int ColunaCasaDestino) {
        getTabuleiro().getCasas()[LinhaCasaDestino][ColunaCasaDestino].setPedra(getTabuleiro().getCasas()[linhaCasaOrigem][ColunaCasaOrigem].getPedra());
        getTabuleiro().getCasas()[linhaCasaOrigem][ColunaCasaOrigem].retiraPedra();
        getTabuleiro().getCasas()[LinhaCasaDestino][ColunaCasaDestino].setForeground(getTabuleiro().getCasas()[getOldX()][getOldY()].getForeground());
        getTabuleiro().getCasas()[linhaCasaOrigem][ColunaCasaOrigem].setForeground(getTabuleiro().getCasas()[getOldX()][getOldY()].getBackground());
        if (((getJogadorDaVez() == 0) && (LinhaCasaDestino == 7)) || ((getJogadorDaVez() == 1) && (LinhaCasaDestino == 0))) {
            setDama(LinhaCasaDestino, ColunaCasaDestino);
        }
    }

    private void hideCasaSelecionada() {
        /*
         * Metodo desativaSelecao - Metodo utilizado para desabilitar a
         * selecao apos a jogada ou caso selecionado outra pedra
         */
        for (int linha = 0; linha
                < 8; linha++) {
            for (int coluna = 0; coluna
                    < 8; coluna++) {
                getTabuleiro().getCasas()[linha][coluna].setCasaSelecionada(false, getTabuleiro().getCorCasaEscura());
                setOldX(-1);
                setOldY(-1);
            }
        }
    }

    /*
     * remove e pedra
     */
    private void retiraPeca(int x, int y) {
        getTabuleiro().getCasas()[x][y].retiraPedra();
        getTabuleiro().getCasas()[x][y].setForeground(getTabuleiro().getCasas()[x][y].getBackground());
    }

    /*
     * Metodo criado para desmarcar as casas possiveis selecionadas.
     */
    private void hideMovimentosPossiveis() {
        for (int linha = 0; linha
                < 8; linha++) {
            for (int coluna = 0; coluna
                    < 8; coluna++) {
                getTabuleiro().getCasas()[coluna][linha].setMovimentoPossivel(false, getTabuleiro().getCorCasaEscura());
            }
        }
    }

    /*
     * Metodo criado para testar todos os movimentos possiveis em todas as direcoes, usada pela pedra configurada como Dama.
     */
    private void testaMovimentosDama(int x, int y, boolean reanalise) {
        int linha = x;
        int coluna = y;
        boolean continua = true;
        while (getTabuleiro().getCasas()[linha++][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha--][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha--][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getTabuleiro().getCasas()[linha++][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
    }

    /*
     * Metodo analisaTipoMovimento
     * Usado para ver qual tipo de movimento sera realizado, por Peca comum ou por Dama
     */
    private void analistaTipoMovimento(int x, int y, boolean reanalise) {
        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 0
                && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x < 7) {
                if (y < 7) {
                    if (getTabuleiro().getCasas()[x + 1][y + 1].isCasaPossivel()
                            && getTabuleiro().getCasas()[x + 1][y + 1].getPedra() != null
                            && getTabuleiro().getCasas()[x + 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && y < 6 && x < 6) {
                        if (getTabuleiro().getCasas()[x + 2][y + 2].isCasaPossivel() && getTabuleiro().getCasas()[x + 2][y + 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x + 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x + 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x + 1][y + 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x + 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getTabuleiro().getCasas()[x + 1][y - 1].isCasaPossivel()
                            && getTabuleiro().getCasas()[x + 1][y - 1].getPedra() != null
                            && getTabuleiro().getCasas()[x + 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && x < 6 && y > 1) {
                        if (getTabuleiro().getCasas()[x + 2][y - 2].isCasaPossivel() && getTabuleiro().getCasas()[x + 2][y - 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x + 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x + 1][y - 1].isCasaPossivel() && getTabuleiro().getCasas()[x + 1][y - 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x + 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                    && getTabuleiro().getCasas()[x][y].getPedra() != null
                    && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 0) {
                testaMovimentosDama(x, y, reanalise);
            }
        }

        if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                && getTabuleiro().getCasas()[x][y].getPedra() != null
                && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 1
                && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x > 0) {
                if (y < 7) {
                    if (getTabuleiro().getCasas()[x - 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y + 1].getPedra() != null && getTabuleiro().getCasas()[x - 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez() && y < 6 && x > 1) {
                        if (getTabuleiro().getCasas()[x - 2][y + 2].isCasaPossivel() && getTabuleiro().getCasas()[x - 2][y + 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x - 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x - 1][y + 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y + 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x - 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getTabuleiro().getCasas()[x - 1][y - 1].isCasaPossivel() && getTabuleiro().getCasas()[x - 1][y - 1].getPedra() != null && getTabuleiro().getCasas()[x - 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez() && x > 1 && y > 1) {
                        if (getTabuleiro().getCasas()[x - 2][y - 2].isCasaPossivel() && getTabuleiro().getCasas()[x - 2][y - 2].getPedra() == null) {
                            getTabuleiro().getCasas()[x - 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getTabuleiro().getCasas()[x - 1][y - 1].getPedra() == null && !reanalise) {
                        getTabuleiro().getCasas()[x - 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getTabuleiro().getCasas()[x][y].isCasaPossivel()
                    && getTabuleiro().getCasas()[x][y].getPedra() != null
                    && getTabuleiro().getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getTabuleiro().getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 1) {
                testaMovimentosDama(x, y, reanalise);
            }
        }
    }

    /*
     * Metodo Estuda Rotina que valida todas as movimentacoes possiveis de uma pedra
     */
    private boolean estudaJogadas(int linha, int coluna, boolean reanalise) {
        /*
         * Primeira validacao eh responsavel por parar as analises na direcao que foi encontrado peca igual a do jogador atual
         */
        if (getTabuleiro().getCasas()[linha][coluna].isCasaPossivel() && getTabuleiro().getCasas()[linha][coluna].getPedra() != null && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() == getJogadorDaVez()) {
            return false;
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha < 7
                && coluna < 7
                && linha > getOldX() && coluna > getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha + 1][coluna + 1].getPedra() != null
                && getTabuleiro().getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna < 7
                    && linha > getOldX() && coluna > getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha + 1][coluna + 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha + 1][coluna + 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha < 7
                && coluna > 0
                && linha > getOldX() && coluna < getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha + 1][coluna - 1].getPedra() != null
                && getTabuleiro().getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna > 0
                    && linha > getOldX() && coluna < getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha + 1][coluna - 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha + 1][coluna - 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha > 0
                && coluna > 0
                && linha < getOldX() && coluna < getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha - 1][coluna - 1].getPedra() != null
                && getTabuleiro().getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna > 0
                    && linha < getOldX() && coluna < getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha - 1][coluna - 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha - 1][coluna - 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha > 0
                && coluna < 7
                && linha < getOldX() && coluna > getOldY()
                && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getTabuleiro().getCasas()[linha - 1][coluna + 1].getPedra() != null
                && getTabuleiro().getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna < 7
                    && linha < getOldX() && coluna > getOldY()
                    && getTabuleiro().getCasas()[linha][coluna].isCasaPossivel()
                    && getTabuleiro().getCasas()[linha][coluna].getPedra() != null
                    && getTabuleiro().getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getTabuleiro().getCasas()[linha - 1][coluna + 1].getPedra() == null
                    && getTabuleiro().getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
                getTabuleiro().getCasas()[linha - 1][coluna + 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Caso nao tenha localizado duas perdras consecutivas
         * em alguma das direcoes, seta casa como possivel de movimento.
         */
        if (getTabuleiro().getCasas()[linha][coluna].getPedra() == null && !reanalise) {
            getTabuleiro().getCasas()[linha][coluna].setMovimentoPossivel(true, Color.cyan);
            return true;
        }
        return false;
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
    public void setJogadores(int idx, Jogador j) {
        jogadores[idx] = j;
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

    /**
     * @return the novaJogada
     */
    public boolean isNovaJogada() {
        return novaJogada;
    }

    /**
     * @param novaJogada the novaJogada to set
     */
    public void setNovaJogada(boolean novaJogada) {
        this.novaJogada = novaJogada;
    }

    /**
     * @return the jogadorDaVez
     */
    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    /**
     * @param jogadorDaVez the jogadorDaVez to set
     */
    private void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    /**
     * @param xSelecionado the xSelecionado to set
     */
    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    /**
     * @return the xSelecionado
     */
    public int getOldX() {
        return oldX;
    }

    /**
     * @return the ySelecionado
     */
    public int getOldY() {
        return oldY;
    }

    /**
     * @param ySelecionado the ySelecionado to set
     */
    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    private void mudarJogadorVez() {
        if (getJogadorDaVez() == 1) {
            setJogadorDaVez(0);
        } else {
            setJogadorDaVez(1);
        }
    }
}
