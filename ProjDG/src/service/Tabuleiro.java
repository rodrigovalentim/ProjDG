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
    private String jogadorDaVez;
    private int xSelecionado;
    private int ySelecionado;

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

        int id = 0;
        this.casas = new Casa[8][8];

        for (int coluna = 0; coluna < casas.length; coluna++) {
            for (int linha = 0; linha < casas[0].length; linha++) {
                if (coluna % 2 != 0) {
                    if (linha % 2 != 0) {
                        casas[coluna][linha] = new Casa(id, Color.gray);
                        id++;
                    } else {
                        casas[coluna][linha] = new Casa(id, Color.lightGray);
                        id++;
                    }
                } else {
                    if (linha % 2 != 0) {
                        casas[coluna][linha] = new Casa(id, Color.gray);
                        id++;
                    } else {
                        casas[coluna][linha] = new Casa(id, Color.lightGray);
                        id++;
                    }
                }
                /*
                 * adicionaComponente foi criado para atribuir os componentes "casas" ao tabuleiro
                 * passando com parametro as casas, a linha, coluna e os tamanhos por padrao 1 e 1
                 *
                 * Adiciona componente (casas -> extend javax.swing.JPanel) ao tabuleiro -> extend JInternalFrame
                 */
                adicionaComponente(getCasas()[coluna][linha], coluna, linha, 1, 1);
            }
        }

        setJogadorDaVez("0");
        montaTabuleiroArray();
        setSize(400, 400);
    }

    public void montaTabuleiroArray() {
        for (int coluna = 0; coluna < 8; coluna++) {
            for (int linha = 0; linha < 8; linha++) {
                if (coluna % 2 == 0) {
                    if (linha % 2 == 0) {
                        setDesenhaTabuleiroMatriz(coluna, linha, "@"); //Casas Claras das linhas pares
                    } else {
                        setDesenhaTabuleiroMatriz(coluna, linha, "#"); //Casas Escuras das linhas pres
                    }
                } else {
                    if (linha % 2 != 0) {
                        setDesenhaTabuleiroMatriz(coluna, linha, "@"); //Casas Claras das linhas impares
                    } else {
                        setDesenhaTabuleiroMatriz(coluna, linha, "#"); //Casas Escuras das linhas impares
                    }
                }
                /*
                 * Casas possiveis de uso (escuras) as configuradas com # para uso posterior
                 */
                if (getDesenhaTabuleiroMatriz(coluna, linha).equals("#")) {
                    getCasas()[coluna][linha].setBackground(Color.gray);
                    getCasas()[coluna][linha].setForeground(Color.gray);
                } else {
                    getCasas()[coluna][linha].setBackground(Color.lightGray);
                    getCasas()[coluna][linha].setForeground(Color.lightGray);
                }
            }
        }
    }

    private void exibeJogadasPossiveis(int x, int y) {
        //TODO
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
        int coluna = (e.getX() / (super.getWidth() / 8));
        int linha = (e.getY() / (super.getHeight() / 8));
        System.out.println("Clicado -> Coluna " +coluna+" linha "+linha);
        System.out.println(getDesenhaTabuleiroMatriz(linha, coluna));
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
         * A divisão por 8 e necessaria por que temos 8 casas
         *
         */

        int coluna = (e.getX() / (super.getWidth() / 8));
        int linha = (e.getY() / (super.getHeight() / 8));

        if (!getDesenhaTabuleiroMatriz(linha, coluna).equals("@") && !getDesenhaTabuleiroMatriz(linha, coluna).equals("#")) {
            getCasas()[linha][coluna].setCasaSelecionada(true, Color.red);
            setxSelecionado(coluna);
            setySelecionado(linha);
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
    public String getDesenhaTabuleiroMatriz(int x, int y) {
        return tabuleiroMatriz[x][y];
    }

    /**
     * @param tabuleiro the tabuleiro to set
     */
    public void setDesenhaTabuleiroMatriz(int x, int y, String val) {
        this.tabuleiroMatriz[x][y] = val;
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
}
