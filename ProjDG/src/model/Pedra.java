package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Pedra extends javax.swing.JPanel {

    private int idOwner;
    private int idPedra;
    private Color cor;
    private BufferedImage imagem;

    public Pedra(int idowner, int idPedra, Color cor) {
        setIdOwner(idowner);
        setIdPedra(idPedra);
        insereImagem();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        /*
         * super.paintComponent(g);  - chamando construtor do JPanel, swing lhe ajuda a desenhar a tela
         */
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.drawImage(getImagem(), 0, 0, this.getWidth() + 1, this.getHeight(), this);
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int id) {
        this.idOwner = id;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public abstract String identificaPedra();

    public abstract void insereImagem();

    /**
     * @return the idPedra
     */
    public int getIdPedra() {
        return idPedra;
    }

    /**
     * @param idPedra the idPedra to set
     */
    public void setIdPedra(int idPedra) {
        this.idPedra = idPedra;
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
        repaint();
    }
}
