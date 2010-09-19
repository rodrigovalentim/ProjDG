package service;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import model.Pedra;

/*
 * A classe tabuleiro sera extendida do InternalFrame,
 * que por ser leve, consome menos recurso, nos atende perfeitamente.
 *
 * Documentacao usada para decidir sobre o InternalFrame
 * http://java.sun.com/docs/books/tutorial/uiswing/components/internalframe.html
 *
 * Foi necessario implementar o MouseListener para capturar o movimento das pecas
 *
 */
public class Tabuleiro extends JInternalFrame implements MouseListener {

    private Casa[][] casas;
    private Container tabuleiroContainer;
    private GridBagLayout tabuleiroGridBagLayout;
    private GridBagConstraints tabuleiroGBConstraints;
    private Color corCasaClara;
    private Color corCasaEscura;
    private boolean novaJogada;
    private int jogadorDaVez;
    private int oldX;
    private int oldY;
    private boolean reAnaliseMovimento;
    private static final int valAjusteClick = 10;
    private ArrayList<Pedra> pedrasCapturadas;

    public Tabuleiro() {
        /*
         * Adicionando o "escutador" de eventos do mouse.
         * com isso e possivel pegar os clicks do mesmo e saber quais pecas estao querendo movimentar
         */
        addMouseListener(this);
        /*
         * Inicializando Variaveis
         */
        this.setOldX(-1);
        this.setOldY(-1);
        /*
         * getContentPane - retorna o conteudo do painel para esse frame
         * assim o metodo setTabuleiroContainer podera pegar o conteudo do
         * frame e colocar no recipiente "tabuleiro"
         */
        setTabuleiroContainer(getContentPane());
        /*
         *
         */
        tabuleiroGridBagLayout = new GridBagLayout();
        getTabuleiroContainer().setLayout(getTabuleiroGridBagLayout());
        /*
         * A classe GridBagConstraints especifica as restrincoes para os componentes
         * que sao definidos usando a classe GridBagLayout.
         */
        tabuleiroGBConstraints = new GridBagConstraints();
        /*
         * A rotina abaixo faz com que as pecas fiquem uniformes
         * sem espacamento entre elas.
         */
        getTabuleiroGBConstraints().fill = GridBagConstraints.BOTH;

        /*
         * As opcoes abaixo, quando setadas com valores positivos, diz que a borda do InternalFrame (IF)
         * tera este valor, ou seja, nao sobrara espaco em branco para exibicao do background do IF
         *
         * Se configuradas com valores negativos, ai a borda aparecera
         */

        getTabuleiroGBConstraints().weightx = 1;
        getTabuleiroGBConstraints().weighty = 1;
        /*
         * Casa escura - Marrom
         * Casa clara - Creme
         */
        setCorCasaClara(new Color(255, 240, 225));
        setCorCasaEscura(new Color(145, 72, 0));
        pedrasCapturadas = new ArrayList<Pedra>();

        int id = 0;
        this.casas = new Casa[8][8];

        for (int linha = 0; linha < casas.length; linha++) {
            for (int coluna = 0; coluna < casas[0].length; coluna++) {
                if (linha % 2 != 0) {
                    if (coluna % 2 != 0) {
                        casas[linha][coluna] = new Casa(id, getCorCasaClara(), false);
                        id++;
                    } else {
                        casas[linha][coluna] = new Casa(id, getCorCasaEscura(), true);
                        id++;
                    }
                } else {
                    if (coluna % 2 != 0) {
                        casas[linha][coluna] = new Casa(id, getCorCasaEscura(), true);
                        id++;
                    } else {
                        casas[linha][coluna] = new Casa(id, getCorCasaClara(), false);
                        id++;
                    }
                }
                /*
                 * adicionaComponente foi criado para atribuir os componentes "casas" ao tabuleiro
                 * passando com parametro as casas, a linha, coluna e os tamanhos por padrao 1 e 1
                 *
                 * Adiciona componente (casas -> extend javax.swing.JPanel) ao tabuleiro -> extend JInternalFrame
                 */
                adicionaComponente(getCasas()[linha][coluna], linha, coluna, 1, 1);
            }
        }
        setJogadorDaVez(0);
        setTitle("Tabuleiro");
        setSize(700, 670);
        setReAnaliseMovimento(false);
    }

    public Casa[][] getCasas() {
        return casas;
    }
    /*
     * AdicionaComponente foi criado para incluir o componente casa no container tabuleiro
     */

    private void adicionaComponente(Component comp, int linha, int coluna, int width, int height) {
        getTabuleiroGBConstraints().gridx = coluna;
        getTabuleiroGBConstraints().gridy = linha;
        getTabuleiroGBConstraints().gridwidth = width;
        getTabuleiroGBConstraints().gridheight = height;
        getTabuleiroGridBagLayout().setConstraints(comp, getTabuleiroGBConstraints());
        getTabuleiroContainer().add(comp);
    }

    public void mostra(JDesktopPane main) {
        main.add(this);
        setOpaque(true);
        show();
    }

    /*
     * Metodo MOVE remove a pedra da casa de origem e coloca o mesmo na casa de destino.
     * Caso a casa de destino seja a primeira linha superior (posicao 0 do array) ou inferior (posicao 7 do array)
     * O metodo promovePedra eh chamado.
     */
    private void move(int linhaCasaOrigem, int ColunaCasaOrigem, int LinhaCasaDestino, int ColunaCasaDestino) {
        getCasas()[LinhaCasaDestino][ColunaCasaDestino].setPedra(getCasas()[linhaCasaOrigem][ColunaCasaOrigem].getPedra());
        getCasas()[linhaCasaOrigem][ColunaCasaOrigem].retiraPedra();
        getCasas()[LinhaCasaDestino][ColunaCasaDestino].setForeground(getCasas()[getOldX()][getOldY()].getForeground());
        getCasas()[linhaCasaOrigem][ColunaCasaOrigem].setForeground(getCasas()[getOldX()][getOldY()].getBackground());
        if (((getJogadorDaVez() == 0) && (LinhaCasaDestino == 7)) || ((getJogadorDaVez() == 1) && (LinhaCasaDestino == 0))) {
            promovePedra(LinhaCasaDestino, ColunaCasaDestino);
        }
    }

    /*
     * Metodo promovePedra tem a finalidade configurar a pedra atual para Dama
     * - Com isso, criamos o objeto Dama na variavel pedra
     * - Removemos a pedra da casa atual
     * - Colocamos a variavel pedra, que contem o objeto dama na casa
     */
    private void promovePedra(int x, int y) {
        Pedra pedra = new Dama(getJogadorDaVez(), getCasas()[x][y].getPedra().getIdPedra(), getCasas()[x][y].getPedra().getCor());
        getCasas()[x][y].retiraPedra();
        getCasas()[x][y].setPedra(pedra);
    }

    public void mouseClicked(MouseEvent e) {
        /*
         * Testes de X e Y.
         */
//        int y = ((e.getX()) / (super.getWidth() / 8));
//        int x = ((e.getY() - valAjuste) / (super.getHeight() / 8));
//        System.out.println("mouseClicked -> linha ->" + x + " coluna ->" + y + " vez = " + getJogadorDaVez());
//        if (getCasas()[x][y].isCasaPossivel()) {
//            if (getCasas()[x][y].getPedra() == null) {
//                System.out.println("Casa Desocupada");
//            } else {
////                System.out.println(getCasas()[x][y].getPedra().getIdOwner() + " " + getCasas()[x][y].getPedra().identificaPedra());
//            }
//        } else {
//            System.out.println("casa nao eh possivel ser usada");
//        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
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

        int y = (e.getX()) / (super.getWidth() / 8);
        int x = (e.getY() - valAjusteClick) / (super.getHeight() / 8);
        int pecaClick = - 1;

        setNovaJogada(false);
        if (getCasas()[x][y].isMovimentoPossivel()) {
            if (Math.abs(getOldY() - y) >= 2) {
                int l = getOldX(), c = getOldY();
                if (x > l && y > c) {
                    while (getCasas()[l++][c++].isCasaPossivel()
                            && l < x
                            && c < y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x > l && y < c) {
                    while (getCasas()[l++][c--].isCasaPossivel()
                            && l < x
                            && c > y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y > c) {
                    while (getCasas()[l--][c++].isCasaPossivel()
                            && l > x
                            && c < y) {
                        retiraPeca(l, c);
                    }
                }
                l = getOldX();
                c = getOldY();
                if (x < l && y < c) {
                    while (getCasas()[l--][c--].isCasaPossivel()
                            && l > x
                            && c > y) {
                        retiraPeca(l, c);
                    }
                }
                move(getOldX(), getOldY(), x, y);
                /*
                 * Implementando rotina para comer n pecas se possivel
                 */
                setOldX(x);
                setOldY(y);
                setReAnaliseMovimento(true);
                System.out.println("Inicia re-analise movimentos [" + x + "][" + y + "]");
                analistaTipoMovimento(x, y, isReAnaliseMovimento());
            } else {
                move(getOldX(), getOldY(), x, y);
            }
            if (!isNovaJogada()) {
                mudarJogadorVez();
                setReAnaliseMovimento(false);
            }
        }
        hideCasaSelecionada();
        hideMovimentosPossiveis();
        /*
         * Remove selecao feita ao clicar em qualquer parte do tabuleiro.
         */
        if (getCasas()[x][y].isCasaPossivel()
                && getCasas()[x][y].getPedra() != null
                && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()) {
            /*
             * seta como selecionada a casa, pintando sua borda
             */
            getCasas()[x][y].setCasaSelecionada(true, Color.black);
            setOldX(x);
            setOldY(y);
            analistaTipoMovimento(getOldX(), getOldY(), isReAnaliseMovimento());
        }
    }

    /*
     * Metodo analisaTipoMovimento
     * Usado para ver qual tipo de movimento sera realizado, por Peca comum ou por Dama
     */
    private void analistaTipoMovimento(int x, int y, boolean reanalise) {
        if (getCasas()[x][y].isCasaPossivel()
                && getCasas()[x][y].getPedra() != null
                && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 0
                && getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x < 7) {
                if (y < 7) {
                    if (getCasas()[x + 1][y + 1].isCasaPossivel()
                            && getCasas()[x + 1][y + 1].getPedra() != null
                            && getCasas()[x + 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && y < 6 && x < 6) {
                        if (getCasas()[x + 2][y + 2].isCasaPossivel() && getCasas()[x + 2][y + 2].getPedra() == null) {
                            getCasas()[x + 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getCasas()[x + 1][y + 1].isCasaPossivel() && getCasas()[x + 1][y + 1].getPedra() == null && !reanalise) {
                        getCasas()[x + 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getCasas()[x + 1][y - 1].isCasaPossivel()
                            && getCasas()[x + 1][y - 1].getPedra() != null
                            && getCasas()[x + 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez()
                            && x < 6 && y > 1) {
                        if (getCasas()[x + 2][y - 2].isCasaPossivel() && getCasas()[x + 2][y - 2].getPedra() == null) {
                            getCasas()[x + 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getCasas()[x + 1][y - 1].isCasaPossivel() && getCasas()[x + 1][y - 1].getPedra() == null && !reanalise) {
                        getCasas()[x + 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getCasas()[x][y].isCasaPossivel()
                    && getCasas()[x][y].getPedra() != null
                    && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 0) {
                testaMovimentosDama(x, y, reanalise);
            }
        }

        if (getCasas()[x][y].isCasaPossivel()
                && getCasas()[x][y].getPedra() != null
                && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                && getJogadorDaVez() == 1
                && getCasas()[x][y].getPedra().identificaPedra().equals("peca")) {
            if (x > 0) {
                if (y < 7) {
                    if (getCasas()[x - 1][y + 1].isCasaPossivel() && getCasas()[x - 1][y + 1].getPedra() != null && getCasas()[x - 1][y + 1].getPedra().getIdOwner() != getJogadorDaVez() && y < 6 && x > 1) {
                        if (getCasas()[x - 2][y + 2].isCasaPossivel() && getCasas()[x - 2][y + 2].getPedra() == null) {
                            getCasas()[x - 2][y + 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getCasas()[x - 1][y + 1].isCasaPossivel() && getCasas()[x - 1][y + 1].getPedra() == null && !reanalise) {
                        getCasas()[x - 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getCasas()[x - 1][y - 1].isCasaPossivel() && getCasas()[x - 1][y - 1].getPedra() != null && getCasas()[x - 1][y - 1].getPedra().getIdOwner() != getJogadorDaVez() && x > 1 && y > 1) {
                        if (getCasas()[x - 2][y - 2].isCasaPossivel() && getCasas()[x - 2][y - 2].getPedra() == null) {
                            getCasas()[x - 2][y - 2].setMovimentoPossivel(true, Color.red);
                            setNovaJogada(true);
                        }
                    }
                    if (getCasas()[x - 1][y - 1].getPedra() == null && !reanalise) {
                        getCasas()[x - 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        } else {
            if (getCasas()[x][y].isCasaPossivel()
                    && getCasas()[x][y].getPedra() != null
                    && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()
                    && getCasas()[x][y].getPedra().identificaPedra().equals("dama")
                    && getJogadorDaVez() == 1) {
                testaMovimentosDama(x, y, reanalise);
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
        while (getCasas()[linha++][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getCasas()[linha--][coluna++].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getCasas()[linha--][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
        linha = x;
        coluna = y;
        continua = true;
        while (getCasas()[linha++][coluna--].isCasaPossivel() && linha <= 7 && coluna <= 7 && linha >= 0 && coluna >= 0 && continua) {
            continua = estudaJogadas(linha, coluna, reanalise);
        }
    }
    /*
     * Metodo Estuda Rotina que valida todas as movimentacoes possiveis de uma pedra
     */

    private boolean estudaJogadas(int linha, int coluna, boolean reanalise) {
        /*
         * Primeira validacao eh responsavel por parar as analises na direcao que foi encontrado peca igual a do jogador atual
         */
        if (getCasas()[linha][coluna].isCasaPossivel() && getCasas()[linha][coluna].getPedra() != null && getCasas()[linha][coluna].getPedra().getIdOwner() == getJogadorDaVez()) {
            return false;
        }
        /*
         * Validacao faz a analise para so continuar analise se localizar 2 pedras seguidas
         */
        if (linha < 7
                && coluna < 7
                && linha > getOldX() && coluna > getOldY()
                && getCasas()[linha][coluna].isCasaPossivel()
                && getCasas()[linha][coluna].getPedra() != null
                && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getCasas()[linha + 1][coluna + 1].getPedra() != null
                && getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna < 7
                    && linha > getOldX() && coluna > getOldY()
                    && getCasas()[linha][coluna].isCasaPossivel()
                    && getCasas()[linha][coluna].getPedra() != null
                    && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getCasas()[linha + 1][coluna + 1].getPedra() == null
                    && getCasas()[linha + 1][coluna + 1].isCasaPossivel()) {
                getCasas()[linha + 1][coluna + 1].setMovimentoPossivel(true, Color.red);
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
                && getCasas()[linha][coluna].isCasaPossivel()
                && getCasas()[linha][coluna].getPedra() != null
                && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getCasas()[linha + 1][coluna - 1].getPedra() != null
                && getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha < 7
                    && coluna > 0
                    && linha > getOldX() && coluna < getOldY()
                    && getCasas()[linha][coluna].isCasaPossivel()
                    && getCasas()[linha][coluna].getPedra() != null
                    && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getCasas()[linha + 1][coluna - 1].getPedra() == null
                    && getCasas()[linha + 1][coluna - 1].isCasaPossivel()) {
                getCasas()[linha + 1][coluna - 1].setMovimentoPossivel(true, Color.red);
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
                && getCasas()[linha][coluna].isCasaPossivel()
                && getCasas()[linha][coluna].getPedra() != null
                && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getCasas()[linha - 1][coluna - 1].getPedra() != null
                && getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna > 0
                    && linha < getOldX() && coluna < getOldY()
                    && getCasas()[linha][coluna].isCasaPossivel()
                    && getCasas()[linha][coluna].getPedra() != null
                    && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getCasas()[linha - 1][coluna - 1].getPedra() == null
                    && getCasas()[linha - 1][coluna - 1].isCasaPossivel()) {
                getCasas()[linha - 1][coluna - 1].setMovimentoPossivel(true, Color.red);
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
                && getCasas()[linha][coluna].isCasaPossivel()
                && getCasas()[linha][coluna].getPedra() != null
                && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                && getCasas()[linha - 1][coluna + 1].getPedra() != null
                && getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
            return false;
        } else {
            if (linha > 0
                    && coluna < 7
                    && linha < getOldX() && coluna > getOldY()
                    && getCasas()[linha][coluna].isCasaPossivel()
                    && getCasas()[linha][coluna].getPedra() != null
                    && getCasas()[linha][coluna].getPedra().getIdOwner() != getJogadorDaVez()
                    && getCasas()[linha - 1][coluna + 1].getPedra() == null
                    && getCasas()[linha - 1][coluna + 1].isCasaPossivel()) {
                getCasas()[linha - 1][coluna + 1].setMovimentoPossivel(true, Color.red);
                setNovaJogada(true);
                return true;
            }
        }
        /*
         * Caso nao tenha localizado duas perdras consecutivas
         * em alguma das direcoes, seta casa como possivel de movimento.
         */
        if (getCasas()[linha][coluna].getPedra() == null && !reanalise) {
            getCasas()[linha][coluna].setMovimentoPossivel(true, Color.cyan);
            if (!reanalise) {
                System.out.println("Analise em todas as direcoes");
            }
            return true;
        }
        return false;
    }

    private void retiraPeca(int x, int y) {
        getCasas()[x][y].retiraPedra();
        getCasas()[x][y].setForeground(getCasas()[x][y].getBackground());
    }

    /*
     * Metodo criado para desmarcar as casas possiveis selecionadas.
     */
    private void hideMovimentosPossiveis() {
        for (int linha = 0; linha
                < 8; linha++) {
            for (int coluna = 0; coluna
                    < 8; coluna++) {
                getCasas()[coluna][linha].setMovimentoPossivel(false, getCorCasaEscura());
            }
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
                getCasas()[linha][coluna].setCasaSelecionada(false, getCorCasaEscura());
                setOldX(-1);
                setOldY(-1);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * @return the xSelecionado
     */
    public int getOldX() {
        return oldX;
    }

    /**
     * @param xSelecionado the xSelecionado to set
     */
    public void setOldX(int oldX) {
        this.oldX = oldX;
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

    /**
     * @return the jogadorDaVez
     */
    private int getJogadorDaVez() {
        return jogadorDaVez;
    }

    /**
     * @param jogadorDaVez the jogadorDaVez to set
     */
    private void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    /**
     * @return the corCasaClara
     */
    private Color getCorCasaClara() {
        return corCasaClara;
    }

    /**
     * @param corCasaClara the corCasaClara to set
     */
    private void setCorCasaClara(Color corCasaClara) {
        this.corCasaClara = corCasaClara;
    }

    /**
     * @return the corCasaEscura
     */
    private Color getCorCasaEscura() {
        return corCasaEscura;
    }

    /**
     * @param corCasaEscura the corCasaEscura to set
     */
    private void setCorCasaEscura(Color corCasaEscura) {
        this.corCasaEscura = corCasaEscura;
    }

    /**
     * @return the tabuleiroContainer
     */
    private Container getTabuleiroContainer() {
        return tabuleiroContainer;
    }

    /**
     * @param tabuleiroContainer the tabuleiroContainer to set
     */
    private void setTabuleiroContainer(Container tabuleiroContainer) {
        this.tabuleiroContainer = tabuleiroContainer;
    }

    /**
     * @return the tabuleiroGridBagLayout
     */
    private GridBagLayout getTabuleiroGridBagLayout() {
        return tabuleiroGridBagLayout;
    }

    /**
     * @param tabuleiroGridBagLayout the tabuleiroGridBagLayout to set
     */
    public void setTabuleiroGridBagLayout(GridBagLayout tabuleiroGridBagLayout) {
        this.tabuleiroGridBagLayout = tabuleiroGridBagLayout;
    }

    /**
     * @return the tabuleiroGBConstraints
     */
    public GridBagConstraints getTabuleiroGBConstraints() {
        return tabuleiroGBConstraints;
    }

    /**
     * @param tabuleiroGBConstraints the tabuleiroGBConstraints to set
     */
    public void setTabuleiroGBConstraints(GridBagConstraints tabuleiroGBConstraints) {
        this.tabuleiroGBConstraints = tabuleiroGBConstraints;
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
     * @return the reAnaliseMovimento
     */
    public boolean isReAnaliseMovimento() {
        return reAnaliseMovimento;
    }

    /**
     * @param reAnaliseMovimento the reAnaliseMovimento to set
     */
    public void setReAnaliseMovimento(boolean reAnaliseMovimento) {
        this.reAnaliseMovimento = reAnaliseMovimento;
    }
}
