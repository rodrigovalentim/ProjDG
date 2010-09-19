package service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import model.Pedra;

public class Casa extends javax.swing.JPanel {

    private int id;
    private Color corCasaSelecionada; //cor quando selecionado
    private boolean casaPossivel;
    private Color corMovimentosPossiveis; //cor dos movimentos possiveis
    private Pedra pedra; //pedra
    private boolean casaSelecionada; //indica casa selecionada
    private boolean movimentoPossivel;
    private Graphics layoutCasa; //layout da casa

    public Casa(int id, Color cor, boolean possivel) {
        setId(id);
        setCasaSelecionada(false, null);
        setLayoutCasa(null);
        this.setBackground(cor);
        this.setForeground(cor);
        setMovimentoPossivel(false, null);
        setCasaPossivel(possivel);
    }

    public void paintComponent(Graphics graphics) {
        layoutCasa = graphics;
        /*
         * super.paintComponent(g);  - chamando construtor do JPanel, swing lhe ajuda a desenhar a tela
         */
        super.paintComponent(graphics);
        /*
         * fillOval - Preenche uma area oval delimitada pelo retangulo especificado e com a cor atual configurada.
         * a cor configurada esta sendo setada a cor da pedra
         *
         * os valores 5 e 5, sao usados para alimentar o x e o y
         * que esta posicionando esta figura da pedra no centro da casa
         *
         * os valores subtraidos da altura e largura, foi definido como 10 para nao ficar uma pedra muito grande.
         *
         */
        graphics.fillOval(5, 5, super.getWidth() - 10, super.getHeight() - 10);
        /*
         * isCasaSelecionada - Ao selecionar, a borda sera pintada da cor escolhida
         * a rotina drawRect pinta o quadrado completo, precisando apenas reduzir
         * a largura e a altura para ele nao pintar em cima da borda.
         * Rotina abaixo pinta 4 linhas, indo da area mais externa (borda) mais pra dentro.
         */
        if (isCasaSelecionada()) {
            layoutCasa.setColor(getCorSelecionado());
            layoutCasa.drawRect(0, 0, super.getWidth() - 1, super.getHeight() - 1);
            layoutCasa.drawRect(1, 1, super.getWidth() - 3, super.getHeight() - 3);
            layoutCasa.drawRect(2, 2, super.getWidth() - 5, super.getHeight() - 5);
            layoutCasa.drawRect(3, 3, super.getWidth() - 7, super.getHeight() - 7);
        }
        /*
         * Pinta possiveis casas
         */
        if (isMovimentoPossivel()) {
            layoutCasa.setColor(getCorPossivel());
            layoutCasa.drawRect(0, 0, super.getWidth() - 1, super.getHeight() - 1);
            layoutCasa.drawRect(1, 1, super.getWidth() - 3, super.getHeight() - 3);
            layoutCasa.drawRect(2, 2, super.getWidth() - 5, super.getHeight() - 5);
            layoutCasa.drawRect(3, 3, super.getWidth() - 7, super.getHeight() - 7);
        }
        /*
         * atualiza cor da pedra
         */
        if (getPedra() != null) {
            setForeground(getPedra().getCor());
        }

        if (getPedra() != null && getPedra().identificaPedra().equals("dama")) {
            layoutCasa.setColor(getBackground());
            /*
             * Calculo exato para pintar o centro das pedras de acordo com a cor do jogador
             */
            graphics.fillOval(super.getWidth() / 4, super.getHeight() / 4, super.getWidth() / 2, super.getHeight() / 2);
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPedra(Pedra pedra) {
        this.pedra = pedra;
    }

    public void retiraPedra() {
        this.setPedra(null);
    }

    /**
     * @return the casaSelecionada
     */
    public boolean isCasaSelecionada() {
        return this.casaSelecionada;
    }

    /**
     * @param casaSelecionada the casaSelecionada to set
     */
    public void setCasaSelecionada(boolean selecionada, Color cor) {
        this.setCorSelecionado(cor); //Setando cor da casa selecionada
        this.casaSelecionada = selecionada; //setando true para casa selecionada
        repaint();
    }

    /**
     * @return the corSelecionado
     */
    public Color getCorSelecionado() {
        return corCasaSelecionada;
    }

    /**
     * @param corSelecionado the corSelecionado to set
     */
    public void setCorSelecionado(Color corSelecionado) {
        this.corCasaSelecionada = corSelecionado;
    }

    /**
     * @return the pedra
     */
    public Pedra getPedra() {
        return pedra;
    }

    /**
     * @return the layoutCasa
     */
    public Graphics getLayoutCasa() {
        return layoutCasa;
    }

    /**
     * @param layoutCasa the layoutCasa to set
     */
    public void setLayoutCasa(Graphics layoutCasa) {
        this.layoutCasa = layoutCasa;
    }

    /*
     * A classe Dimension encapsula a largura e a altura de um componente,
     * usando um numero inteiro de precisao.
     *
     * Os metodos a seguir sao usados pelo layout manager, facilitando o desenvolvimento
     *
     * getPreferredSize - retorna o tamanho "preferido" do componente.
     * e o mesmo vem do componenteUI
     */
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * @return the movimentoPossivel
     */
    public boolean isMovimentoPossivel() {
        return movimentoPossivel;
    }

    /**
     * @param movimentoPossivel the movimentoPossivel to set
     */
    public void setMovimentoPossivel(boolean m, Color c) {
        setCorPossivel(c);
        this.movimentoPossivel = m;
        repaint();
    }

    /**
     * @return the corPossivel
     */
    public Color getCorPossivel() {
        return corMovimentosPossiveis;
    }

    /**
     * @param corPossivel the corPossivel to set
     */
    public void setCorPossivel(Color c) {
        this.corMovimentosPossiveis = c;
    }

    /**
     * @return the possivel
     */
    public boolean isCasaPossivel() {
        return casaPossivel;
    }

    /**
     * @param possivel the possivel to set
     */
    public void setCasaPossivel(boolean possivel) {
        this.casaPossivel = possivel;
    }
}
