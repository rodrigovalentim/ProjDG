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
    private String tabuleiroMatriz[][] = new String[8][8];
    private Container tabuleiroContainer;
    private GridBagLayout tabuleiroGridBagLayout;
    private GridBagConstraints tabuleiroGBConstraints;
    private Color corCasaClara;
    private Color corCasaEscura;
    private String jogadorDaVez;
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
                        casas[linha][coluna] = new Casa(id, getCorCasaClara());
                        id++;
                    } else {
                        casas[linha][coluna] = new Casa(id, getCorCasaEscura());
                        id++;
                    }
                } else {
                    if (coluna % 2 != 0) {
                        casas[linha][coluna] = new Casa(id, getCorCasaEscura());
                        id++;
                    } else {
                        casas[linha][coluna] = new Casa(id, getCorCasaClara());
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

        setJogadorDaVez("0");
        montaTabuleiroArray();
        setTitle("Tabuleiro");
        setSize(700, 670);
    }

    public void montaTabuleiroArray() {

        for (int linha = 0; linha < casas.length; linha++) {
            for (int coluna = 0; coluna < casas[0].length; coluna++) {
                if (linha % 2 != 0) {
                    if (coluna % 2 != 0) {
                        setDesenhaTabuleiroMatriz(linha, coluna, "@"); //Casas Claras das linhas pares
                    } else {
                        setDesenhaTabuleiroMatriz(linha, coluna, "#"); //Casas Escuras das linhas pres
                    }
                } else {
                    if (coluna % 2 != 0) {
                        setDesenhaTabuleiroMatriz(linha, coluna, "#"); //Casas Claras das linhas impares
                    } else {
                        setDesenhaTabuleiroMatriz(linha, coluna, "@"); //Casas Escuras das linhas impares
                    }
                }
                /*
                 * Casas possiveis de uso (escuras) as configuradas com # para uso posterior
                 */
                if (getDesenhaTabuleiroMatriz(linha, coluna).equals("#")) {
                    getCasas()[linha][coluna].setBackground(getCorCasaEscura());
                    getCasas()[linha][coluna].setForeground(getCorCasaEscura());
                } else {
                    getCasas()[linha][coluna].setBackground(getCorCasaClara());
                    getCasas()[linha][coluna].setForeground(getCorCasaClara());
                }
            }
        }
    }

    private void exibeJogadasPossiveis(int x, int y) {
        if (getDesenhaTabuleiroMatriz(x, y).equals(getJogadorDaVez()) && getJogadorDaVez().equals("0")) {
            if (x < 7) {
                if (y < 7) {
                    /*
                     * Se ainda nao estiver proximo da ultima casa, valido as posicoes abaixo
                     *
                     */
                    if (getDesenhaTabuleiroMatriz(x + 1, y + 1).equals("1") && y < 6 && x < 6) {
                        if (getDesenhaTabuleiroMatriz(x + 2, +2).equals("#")) {
                            getCasas()[x + 2][y + 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getDesenhaTabuleiroMatriz(x + 1, y + 1).equals("#")) {
                        getCasas()[x + 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getDesenhaTabuleiroMatriz(x + 1, y - 1).equals("1") && x < 6 && y > 1) {
                        if (getDesenhaTabuleiroMatriz(x + 2, y - 2).equals("#")) {
                            getCasas()[x + 2][y - 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getDesenhaTabuleiroMatriz(x + 1, y - 1).equals("#")) {
                        getCasas()[x + 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        }


        if (getDesenhaTabuleiroMatriz(x, y).equals(getJogadorDaVez()) && getJogadorDaVez().equals("1")) {
            if (x > 0) {
                if (y < 7) {
                    if (getDesenhaTabuleiroMatriz(x - 1, y + 1).equals("0") && y < 6 && x > 1) {
                        if (getDesenhaTabuleiroMatriz(x - 2, y + 2).equals("#")) {
                            getCasas()[x - 2][y + 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getDesenhaTabuleiroMatriz(x - 1, y + 1).equals("#")) {
                        getCasas()[x - 1][y + 1].setMovimentoPossivel(true, Color.black);
                    }
                }
                if (y > 0) {
                    if (getDesenhaTabuleiroMatriz(x - 1, y - 1).equals("0") && x > 1 && y > 1) {
                        if (getDesenhaTabuleiroMatriz(x - 2, y - 2).equals("#")) {
                            getCasas()[x - 2][y - 2].setMovimentoPossivel(true, Color.black);
                        }
                    }
                    if (getDesenhaTabuleiroMatriz(x - 1, y - 1).equals("#")) {
                        getCasas()[x - 1][y - 1].setMovimentoPossivel(true, Color.black);
                    }
                }
            }
        }
    }

    public Casa[][] getCasas() {
        return casas;
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
    /*
     * AdicionaComponente foi criado para incluir o componente casa no container tabuleiro
     */

    public void adicionaComponente(Component c, int linha, int coluna, int width, int height) {
        getTabuleiroGBConstraints().gridx = coluna;
        getTabuleiroGBConstraints().gridy = linha;
        getTabuleiroGBConstraints().gridwidth = width;
        getTabuleiroGBConstraints().gridheight = height;
        getTabuleiroGridBagLayout().setConstraints(c, getTabuleiroGBConstraints());
        getTabuleiroContainer().add(c);
    }

    public void mostra(JDesktopPane main) {
        main.add(this);
        setOpaque(true);
        show();
    }

    public void move(int colunaOrigem, int linhaOrigem, int colunaDestino, int linhaDestino) {

    }

    public void mouseClicked(MouseEvent e) {
        /*
         * Testes de X e Y.
         */
        int y = ((e.getX()) / (super.getWidth() / 8));
        int x = ((e.getY() - valAjuste) / (super.getHeight() / 8));
        System.out.println("Clicado -> linha ->" + x + " coluna ->" + y);
        System.out.println(getDesenhaTabuleiroMatriz(x, y));
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
        this.desmarcaSelecao();

        if (!getDesenhaTabuleiroMatriz(x, y).equals("@") && !getDesenhaTabuleiroMatriz(x, y).equals("#")) {
            /*
             * seta como selecionada a casa, pintando sua borda
             */
            getCasas()[x][y].setCasaSelecionada(true, Color.black);
            setxSelecionado(x);
            setySelecionado(y);
            this.exibeJogadasPossiveis(getxSelecionado(), getySelecionado());
        }

    }

    private void desmarcaSelecao() {
        /*
         * Metodo desativaSelecao - Metodo utilizado para desabilitar a
         * seleção apos a jogada ou caso selecionado outra pedra
         */
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                getCasas()[linha][coluna].setCasaSelecionada(false, Color.white);
                setxSelecionado(-1);
                setySelecionado(-1);
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
     * @return the tabuleiro
     */
    public String getDesenhaTabuleiroMatriz(int linha, int coluna) {
        return tabuleiroMatriz[linha][coluna];
    }

    /**
     * @param tabuleiro the tabuleiro to set
     */
    public void setDesenhaTabuleiroMatriz(int linha, int coluna, String val) {
        this.tabuleiroMatriz[linha][coluna] = val;
    }

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
        if (getJogadorDaVez().equals("1")) {
            setJogadorDaVez("0");
        } else {
            setJogadorDaVez("1");
        }
    }

    /**
     * @return the jogadorDaVez
     */
    public String getJogadorDaVez() {
        return jogadorDaVez;
    }

    /**
     * @param jogadorDaVez the jogadorDaVez to set
     */
    public void setJogadorDaVez(String jogadorDaVez) {
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
}
