package service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import model.Pedra;

public class Casa extends javax.swing.JPanel {

    private int id;
    private Color corSelecionado; //cor quando selecionado
    private Color corPedra; //cor da pedra
    private Color corPossivel;
    private Pedra pedra; //pedra
    private boolean casaSelecionada; //indica casa selecionada
    private boolean movimentoPossivel;
    private Graphics layoutCasa; //layout da casa

    public Casa(int id, Color cor) {
        setId(id);
        setCor(cor);
        retiraPedra(); //casa comeca sem pedra nenhuma
        setCasaSelecionada(false, null);
        setLayoutCasa(null);
        this.setBackground(cor);
        this.setForeground(cor);
        setMovimentoPossivel(false, null);
    }

    public void paintComponent(Graphics g) {
        layoutCasa = g;
        /*
         * super.paintComponent(g);  - chamando construtor do JPanel, swing lhe ajuda a desenhar a tela
         */
        super.paintComponent(g);
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
        g.fillOval(5, 5, super.getWidth() - 10, super.getHeight() - 10);
        /*
         * isCasaSelecionada - Ao selecionar, a borda sera pintada da cor escolhida
         */
        if (isCasaSelecionada()) {
            layoutCasa.setColor(getCorSelecionado());
            layoutCasa.drawRect(0, 0, super.getWidth() - 1, super.getHeight() - 1);
            layoutCasa.drawRect(1, 1, super.getWidth() - 3, super.getHeight() - 3);
            layoutCasa.drawRect(2, 2, super.getWidth() - 5, super.getHeight() - 5);
            layoutCasa.drawRect(3, 3, super.getWidth() - 7, super.getHeight() - 7);
        }
        if (isMovimentoPossivel()) {
            layoutCasa.setColor(getCorPossivel());
            layoutCasa.drawRect(0, 0, super.getWidth() - 1, super.getHeight() - 1);
            layoutCasa.drawRect(1, 1, super.getWidth() - 3, super.getHeight() - 3);
            layoutCasa.drawRect(2, 2, super.getWidth() - 5, super.getHeight() - 5);
            layoutCasa.drawRect(3, 3, super.getWidth() - 7, super.getHeight() - 7);
        }
    }

    public Color getCor() {
        return this.corPedra;
    }

    public void setCor(Color cor) {
        this.corPedra = cor;
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
        return corSelecionado;
    }

    /**
     * @param corSelecionado the corSelecionado to set
     */
    public void setCorSelecionado(Color corSelecionado) {
        this.corSelecionado = corSelecionado;
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
     * usando um número inteiro de precisão.
     *
     * Os metodos a seguir são usados pelo layout manager, facilitando o desenvolvimento
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
        if (movimentoPossivel) {
            System.out.println("Possivel");
        }
        setCorPossivel(c);
        this.movimentoPossivel = m;
        repaint();
    }

    /**
     * @return the corPossivel
     */
    public Color getCorPossivel() {
        return corPossivel;
    }

    /**
     * @param corPossivel the corPossivel to set
     */
    public void setCorPossivel(Color c) {
        this.corPossivel = c;
    }
}
