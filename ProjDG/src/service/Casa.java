package service;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import model.Pedra;
import utils.ImageLoader;

public class Casa extends javax.swing.JPanel {

    private int id;
    private Color corCasaSelecionada; //cor quando selecionado
    private boolean casaPossivel;
    private Color corMovimentosPossiveis; //cor dos movimentos possiveis
    private Pedra pedra; //pedra
    private boolean casaSelecionada; //indica casa selecionada
    private boolean movimentoPossivel;
    private Graphics layoutCasa; //layout da casa
    private BufferedImage imagem;
    private BufferedImage imagemAreaCasa;

    public Casa(int id, Color cor, boolean possivel, String imgString) {
        setId(id);
        setCasaSelecionada(false, null);
        setLayoutCasa(null);
        setMovimentoPossivel(false, null);
        setCasaPossivel(possivel);
        this.setBackground(cor);
        this.setForeground(cor);
        imagem = new ImageLoader().imageLoader(imgString);
        setImagemAreaCasa(new ImageLoader().imageLoader(imgString));
    }

    public void paintComponent(Graphics graphics) {
        layoutCasa = graphics;
        /*
         * super.paintComponent(g);  - chamando construtor do JPanel, swing lhe ajuda a desenhar a tela
         */
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.drawImage(getImagem(), 0, 0, this.getWidth(), this.getHeight(), this);
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
        //graphics.fillOval(5, 5, super.getWidth() - 10, super.getHeight() - 10);
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
        }
        /*
         * Pinta possiveis casas que podem ser movimentadas
         */
        if (isMovimentoPossivel()) {
            layoutCasa.setColor(getCorPossivel());
            layoutCasa.drawRect(0, 0, super.getWidth() - 1, super.getHeight() - 1);
            layoutCasa.drawRect(1, 1, super.getWidth() - 3, super.getHeight() - 3);
        }
        /*
         * Atualiza cor da pedra
         */
        if (getPedra() != null) {
            //    setForeground(getPedra().getCor());
        }

        if (getPedra() != null && getPedra().identificaPedra().equals("dama")) {
            // layoutCasa.setColor(getBackground());
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
        if (pedra != null) {
            setImagem(pedra.getImagem());
        }
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
     * @param selecionada the casaSelecionada to set
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
    public void setMovimentoPossivel(boolean movimentoPossivel, Color c) {
        setCorPossivel(c);
        this.movimentoPossivel = movimentoPossivel;
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
    public void setCorPossivel(Color corPossivel) {
        this.corMovimentosPossiveis = corPossivel;
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

    /**
     * @return the imagemAreaCasa
     */
    public BufferedImage getImagemAreaCasa() {
        return imagemAreaCasa;
    }

    /**
     * @param imagemAreaCasa the imagemAreaCasa to set
     */
    public void setImagemAreaCasa(BufferedImage imagemAreaCasa) {
        this.imagemAreaCasa = imagemAreaCasa;
    }

    /**
     * @return the imagem
     */
    public BufferedImage getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
        this.repaint();
    }
}
