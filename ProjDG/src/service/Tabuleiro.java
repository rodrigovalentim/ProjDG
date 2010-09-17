package service;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/*
 * A classe tabuleiro sera extendida do InternalFrame,
 * que por ser leve, consome menos recurso, nos atende perfeitamente.
 *
 * Documentacao usada para decidir sobre o InternalFrame
 * http://java.sun.com/docs/books/tutorial/uiswing/components/internalframe.html
 *
 * Foi necessario implementar o mouseListener e o MouseMotionListen
 *
 */
public class Tabuleiro extends JInternalFrame implements MouseListener, MouseMotionListener {

    private Casa[][] casas;
    private Container tabuleiroContainer;
    private GridBagLayout tabuleiroGridBagLayout;
    private GridBagConstraints tabuleiroGBConstraints;
    private Color corCasaClara;
    private Color corCasaEscura;
    private int jogadorDaVez;
    private int xSelecionado;
    private int ySelecionado;
    private static final int valAjuste = 10;

    public Tabuleiro() {
        /*
         * Adicionando o "escutador" de eventos do mouse.
         * com isso e possivel pegar os clicks do mesmo e saber quais pecas estao querendo movimentar
         */
        addMouseListener(this);
        addMouseMotionListener(this);
        /*
         * Inicializando Variaveis
         */
        this.setxSelecionado(-1);
        this.setySelecionado(-1);
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
        getTabuleiroGBConstraints().fill = GridBagConstraints.BOTH;

        /*
         * Especifica como distribuir o espaco horizontal e vertical extra.
         * Com essa opcao, o gerenciador do layout (manager layout) consegue calcular e
         * gerenciar melhor os espacos extras. por isso que quando usamos o gridLayout,
         * ele coloca todos os objetos lado - a - lado
         */

        getTabuleiroGBConstraints().weightx = 1;
        getTabuleiroGBConstraints().weighty = 1;
        /*
         * Casa escura - Marrom
         * Casa clara - Creme
         */
        setCorCasaClara(new Color(255, 240, 225));
        setCorCasaEscura(new Color(145, 72, 0));

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
    }

    private void showJogadasPossiveis(int x, int y) {
        if (getCasas()[x][y].getPedra().getId() == getJogadorDaVez() && getJogadorDaVez() == 0) {
            if (x < 7) {
                if (y < 7) {
                    if (getCasas()[x + 1][y + 1].getPedra() != null
                            && getCasas()[x + 1][y + 1].getPedra().getId() != getJogadorDaVez()
                            && y < 6 && x < 6) {
                        if (getCasas()[x + 2][y + 2].isCasaPossivel()
                                && getCasas()[x + 2][y + 2].getPedra() == null) {
                            getCasas()[x + 2][y + 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getCasas()[x + 1][y + 1].getPedra() == null) {
                        getCasas()[x + 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getCasas()[x + 1][y - 1].getPedra() != null
                            && getCasas()[x + 1][y - 1].getPedra().getId() != getJogadorDaVez()
                            && x < 6 && y > 1) {
                        if (getCasas()[x + 2][y - 2].isCasaPossivel()
                                && getCasas()[x + 2][y - 2].getPedra() == null) {
                            getCasas()[x + 2][y - 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getCasas()[x + 1][y - 1].isCasaPossivel()
                            && getCasas()[x + 1][y - 1].getPedra() == null) {
                        getCasas()[x + 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
            /*
             * Implementar validacao de dama
             */
        }

        if (getCasas()[x][y].getPedra().getId() == getJogadorDaVez() && getJogadorDaVez() == 1) {
            if (x > 0) {
                if (y < 7) {
                    if (getCasas()[x - 1][y + 1].getPedra() != null
                            && getCasas()[x - 1][y + 1].getPedra().getId() != getJogadorDaVez() && y < 6 && x > 1) {
                        if (getCasas()[x - 2][y + 2].isCasaPossivel()
                                && getCasas()[x - 2][y + 2].getPedra() == null) {
                            getCasas()[x - 2][y + 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getCasas()[x - 1][y + 1].isCasaPossivel()
                            && getCasas()[x - 1][y + 1].getPedra() == null) {
                        getCasas()[x - 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getCasas()[x - 1][y - 1].getPedra() != null
                            && getCasas()[x - 1][y - 1].getPedra().getId() != getJogadorDaVez()
                            && x > 1 && y > 1) {
                        if (getCasas()[x - 2][y - 2].isMovimentoPossivel()
                                && getCasas()[x - 2][y - 2].getPedra() == null) {
                            getCasas()[x - 2][y - 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getCasas()[x - 1][y - 1].getPedra() == null) {
                        getCasas()[x - 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
            /*
             * Implementar validacao de dama
             */
        }
    }

    public Casa[][] getCasas() {
        return casas;
    }

    /*
     * AdicionaComponente foi criado para incluir o componente casa no container tabuleiro
     */
    public void adicionaComponente(Component comp, int linha, int coluna, int width, int height) {
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

    public void move(int linhaOrigem, int ColunaOrigem, int LinhaDestino, int ColunaDestino) {
        Casa origem = getCasas()[linhaOrigem][ColunaOrigem];
        Casa destino = getCasas()[LinhaDestino][ColunaDestino];
        getCasas()[LinhaDestino][ColunaDestino].setPedra(getCasas()[linhaOrigem][ColunaOrigem].getPedra());
        getCasas()[linhaOrigem][ColunaOrigem].retiraPedra();
        destino.setForeground(origem.getForeground());
        origem.setForeground(origem.getBackground());
    }

    public void mouseClicked(MouseEvent e) {
        /*
         * Testes de X e Y.
         */
        int y = ((e.getX()) / (super.getWidth() / 8));
        int x = ((e.getY() - valAjuste) / (super.getHeight() / 8));
        System.out.println("mouseClicked -> linha ->" + x + " coluna ->" + y);
        if (getCasas()[x][y].isCasaPossivel()) {
            if (getCasas()[x][y].getPedra() == null) {
                System.out.println("Casa Desocupada");
            } else {
                System.out.println(getCasas()[x][y].getPedra().getId());
            }
        } else {
            System.out.println("casa nao eh possivel ser usada");
        }
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
         * Y esta recebendo x e X esta recebendo Y por que o mouseEvent interte isso, pra nos ajudar, claro.
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
        int x = (e.getY() - valAjuste) / (super.getHeight() / 8);

        /*
         * remove selecao feita ao clicar na pedra.
         */

        if (getCasas()[x][y].isMovimentoPossivel()) {
        }
        this.hideCasaSelecionada();
        this.hideMovimentosPossiveis();

        if (getCasas()[x][y].isCasaPossivel() && getCasas()[x][y].getPedra() != null) {
            /*
             * seta como selecionada a casa, pintando sua borda
             */
            getCasas()[x][y].setCasaSelecionada(true, Color.black);
            setxSelecionado(x);
            setySelecionado(y);
            this.showJogadasPossiveis(getxSelecionado(), getySelecionado());
        }
    }

    /*
     * Evento criado para desmarcar as casas possiveis selecionadas.
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
                setxSelecionado(-1);
                setySelecionado(-1);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        /*
         * Metodo nao implementado
         */
    }

    public void mouseExited(MouseEvent e) {
        /*
         * Metodo nao implementado
         */
    }

    public void mouseDragged(MouseEvent e) {
        /*
         * Metodo nao implementado
         */
    }

    public void mouseMoved(MouseEvent e) {
        /*
         * Metodo nao implementado
         */
    }

    /**
     * @return the tabuleiro
     */
//    public String getDesenhaTabuleiroMatriz(int linha, int coluna) {
//        return tabuleiroMatriz[linha][coluna];
//    }
    /**
     * @param tabuleiro the tabuleiro to set
     */
//    public void setDesenhaTabuleiroMatriz(int linha, int coluna, String val) {
//        this.tabuleiroMatriz[linha][coluna] = val;
//    }
    /**
     * @return the xSelecionado
     */
    public int getxSelecionado() {
        return xSelecionado;


    }

    /**
     * @param xSelecionado the xSelecionado to set
     */
    public void setxSelecionado(int xSelecionado) {
        this.xSelecionado = xSelecionado;


    }

    /**
     * @return the ySelecionado
     */
    public int getySelecionado() {
        return ySelecionado;


    }

    /**
     * @param ySelecionado the ySelecionado to set
     */
    public void setySelecionado(int ySelecionado) {
        this.ySelecionado = ySelecionado;


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
    public int getJogadorDaVez() {
        return jogadorDaVez;


    }

    /**
     * @param jogadorDaVez the jogadorDaVez to set
     */
    public void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;


    }

    /**
     * @return the corCasaClara
     */
    public Color getCorCasaClara() {
        return corCasaClara;


    }

    /**
     * @param corCasaClara the corCasaClara to set
     */
    public void setCorCasaClara(Color corCasaClara) {
        this.corCasaClara = corCasaClara;


    }

    /**
     * @return the corCasaEscura
     */
    public Color getCorCasaEscura() {
        return corCasaEscura;


    }

    /**
     * @param corCasaEscura the corCasaEscura to set
     */
    public void setCorCasaEscura(Color corCasaEscura) {
        this.corCasaEscura = corCasaEscura;


    }

    /**
     * @return the tabuleiroContainer
     */
    public Container getTabuleiroContainer() {
        return tabuleiroContainer;


    }

    /**
     * @param tabuleiroContainer the tabuleiroContainer to set
     */
    public void setTabuleiroContainer(Container tabuleiroContainer) {
        this.tabuleiroContainer = tabuleiroContainer;


    }

    /**
     * @return the tabuleiroGridBagLayout
     */
    public GridBagLayout getTabuleiroGridBagLayout() {
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
}
