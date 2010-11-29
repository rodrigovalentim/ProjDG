package service;

import java.awt.*;
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
public class Tabuleiro extends JInternalFrame {

    private Casa[][] casas;
    private Container tabuleiroContainer;
    private GridBagLayout tabuleiroGridBagLayout;
    private GridBagConstraints tabuleiroGBConstraints;
    private Color corCasaClara;
    private Color corCasaEscura;
    private ArrayList<Pedra> pedrasCapturadas;
    private int id;
    private final String casaEscura = "imagem/casaescura.png";
    private final String casaClara = "imagem/casaclara.png";

    public Tabuleiro() {
        /*
         * Adicionando o "escutador" de eventos do mouse.
         * com isso e possivel pegar os clicks do mesmo e saber quais pecas estao querendo movimentar
         */

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
         *
         * Também são definidas e largura e coluna de cada componente da constraint
         */

        getTabuleiroGBConstraints().weightx = 1;
        getTabuleiroGBConstraints().weighty = 1;
        getTabuleiroGBConstraints().gridwidth = 1;
        getTabuleiroGBConstraints().gridheight = 1;
        /*
         * Casa escura - Marrom
         * Casa clara - Creme
         */
        setCorCasaClara(new Color(255, 240, 225));
        setCorCasaEscura(new Color(145, 72, 0));
        pedrasCapturadas = new ArrayList<Pedra>();
        System.out.println(this.getWidth() + "" + this.getHeight());
        setId(0);
        this.casas = new Casa[8][8];
        /*
         * Criando as casas do tabuleiro
         */
        for (int linha = 0; linha < casas.length; linha++) {
            for (int coluna = 0; coluna < casas[0].length; coluna++) {
                if (linha % 2 != 0) {
                    if (coluna % 2 != 0) {
                        casas[linha][coluna] = new Casa(getId(), getCorCasaClara(), false, casaClara);
                        setId(getId() + 1);
                    } else {
                        casas[linha][coluna] = new Casa(getId(), getCorCasaEscura(), true, casaEscura);
                        setId(getId() + 1);
                    }
                } else {
                    if (coluna % 2 != 0) {
                        casas[linha][coluna] = new Casa(getId(), getCorCasaEscura(), true, casaEscura);
                        setId(getId() + 1);
                    } else {
                        casas[linha][coluna] = new Casa(getId(), getCorCasaClara(), false, casaClara);
                        setId(getId() + 1);
                    }
                }
                /*
                 * adicionaComponente foi criado para atribuir os componentes "casas" ao tabuleiro
                 * passando com parametro as casas, a linha, coluna e os tamanhos por padrao 1 e 1
                 *
                 * Adiciona componente (casas -> extend javax.swing.JPanel) ao tabuleiro -> extend JInternalFrame
                 */
                adicionaComponente(getCasas()[linha][coluna], linha, coluna);
            }
        }
        setTitle("Tabuleiro"); //Titulo do Tabuleiro
        setSize(600, 520); //Size do do tabuleiro
    }

    /*
     * retorna as Casas
     */
    public Casa[][] getCasas() {
        return casas;
    }
    /*
     * AdicionaComponente foi criado para incluir o componente casa no container tabuleiro
     */

    private void adicionaComponente(Component comp, int linha, int coluna) {
        getTabuleiroGBConstraints().gridx = coluna;
        getTabuleiroGBConstraints().gridy = linha;
        getTabuleiroGridBagLayout().setConstraints(comp, getTabuleiroGBConstraints());
        getTabuleiroContainer().add(comp);
    }

    /*
     * Exibe esta classe em forma grafica no container principal
     */
    public void mostra(JDesktopPane main) {
        main.add(this);
        setOpaque(true);
        show();
    }

    /*
     * Metodo promovePedra tem a finalidade configurar a pedra atual para Dama
     * - Com isso, criamos o objeto Dama na variavel pedra
     * - Removemos a pedra da casa atual
     * - Colocamos a variavel pedra, que contem o objeto dama na casa
     */
    public void promovePedra(int jogadorVez, int x, int y) {
        Pedra pedra = new Dama(jogadorVez, getCasas()[x][y].getPedra().getIdPedra(), getCasas()[x][y].getPedra().getCor(), "teste");
        getCasas()[x][y].retiraPedra();
        getCasas()[x][y].setPedra(pedra);
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
    public Color getCorCasaEscura() {
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
