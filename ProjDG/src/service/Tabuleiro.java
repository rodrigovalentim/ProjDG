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
    private final String casaEscura = "casaescura.png";
    private final String casaClara = "casaclara.png";

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



    /*
     * Rotina usada somente para testar movimentos
     */
//    public void mouseClicked(MouseEvent e) {
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
//    }
//
//    public void mousePressed(MouseEvent e) {
//    }

//    public void mouseReleased(MouseEvent e) {
//        /*
//         * Evento que dispara quando o botao do mouse e solto.
//         *
//         * super.getWidth serve para pegar a largura do componente
//         *
//         * super.getHeight serve para pegar a altura do componente
//         *
//         * A divisao por 8 e necessaria por que temos 8 casas
//         *
//         * Y esta recebendo testaMovimentos e X esta recebendo Y por que o mouseEvent interte isso, pra nos ajudar, claro.
//         * Atraves destes testes, leitura de documentacoes
//         * http://download.oracle.com/javase/1.4.2/docs/api/java/awt/event/MouseEvent.html
//         * indentificamos que o MouseEvent retorna X como sendo linha e o Y como sendo coluna.
//         * Invertendo as posicoes, tudo se resolveu.
//         *
//         *
//         * O valor de ajuste igual a 20, quando subtraido da linha, temos uma maior precisao
//         * no calculo nos possibilitando clicar bem proximo da borda da casa sem que ele pegue
//         * a casa errada. Sem esse valor de ajuste, ao nos aproximarmos das bordas, ele pegava a casa vizinha.
//         *
//         */
//
//        int y = (e.getX()) / (super.getWidth() / 8);
//        int x = (e.getY() - valAjusteClick) / (super.getHeight() / 8);
//        int pecaClick = - 1;
//        /*
//         * Variavel abaixo usada para configurar a reanalise dos movimentos
//         */
//        boolean reAnaliseMovimento = false;
//        /*
//         * Metodo set da rotina que configura novaJogada
//         */
//        setNovaJogada(false);
//        /*
//         * Rotina abaixo remover que foram "comidas" pelo adversario
//         */
//        if (getCasas()[x][y].isMovimentoPossivel()) {
//            /*
//             * Calculo usado para saber se o movimento realizado foi de mais de uma casa
//             * Caso posivito, entra na rotina de analise de possiveis pecas comidas
//             */
//            if (Math.abs(getOldY() - y) >= 2) {
//                int l = getOldX(), c = getOldY();
//                if (x > l && y > c) {
//                    while (getCasas()[l++][c++].isCasaPossivel()
//                            && l < x
//                            && c < y) {
//                        retiraPeca(l, c);
//                    }
//                }
//                l = getOldX();
//                c = getOldY();
//                if (x > l && y < c) {
//                    while (getCasas()[l++][c--].isCasaPossivel()
//                            && l < x
//                            && c > y) {
//                        retiraPeca(l, c);
//                    }
//                }
//                l = getOldX();
//                c = getOldY();
//                if (x < l && y > c) {
//                    while (getCasas()[l--][c++].isCasaPossivel()
//                            && l > x
//                            && c < y) {
//                        retiraPeca(l, c);
//                    }
//                }
//                l = getOldX();
//                c = getOldY();
//                if (x < l && y < c) {
//                    while (getCasas()[l--][c--].isCasaPossivel()
//                            && l > x
//                            && c > y) {
//                        retiraPeca(l, c);
//                    }
//                }
//                /*
//                 * Rotina de movimentacao da peca, da origem pro destino
//                 */
//                move(getOldX(), getOldY(), x, y);
//                setOldX(x);
//                setOldY(y);
//                reAnaliseMovimento = true;
//                /*
//                 * Re analisa os movimentos para saber se e possivel comer mais pedras antes de parar
//                 */
//                analistaTipoMovimento(x, y, reAnaliseMovimento);
//            } else {
//                /*
//                 * Simples rotina de movimento, sem acao de comer
//                 */
//                move(getOldX(), getOldY(), x, y);
//            }
//            /*
//             * Controla vez do jgoador
//             */
//            if (!isNovaJogada()) {
//                mudarJogadorVez();
//                reAnaliseMovimento = false;
//            }
//        }
//        /*
//         * Limpa casas selecionads
//         */
//        hideCasaSelecionada();
//        /*
//         * limpa Movimentos selecionados
//         */
//        hideMovimentosPossiveis();
//
//        if (getCasas()[x][y].isCasaPossivel()
//                && getCasas()[x][y].getPedra() != null
//                && getCasas()[x][y].getPedra().getIdOwner() == getJogadorDaVez()) {
//            /*
//             * seta como selecionada a casa, pintando sua borda
//             */
//            getCasas()[x][y].setCasaSelecionada(true, Color.black);
//            setOldX(x);
//            setOldY(y);
//            analistaTipoMovimento(getOldX(), getOldY(), reAnaliseMovimento);
//        }
//    }

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
