package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import utils.imageLoader;

public abstract class Pedra {

    private int idOwner;
    private int idPedra;
    private Color cor;
    private BufferedImage imagem;

    public Pedra(int idowner, int idPedra, Color cor, String imgString) {
        setIdOwner(idowner);
        setIdPedra(idPedra);
        setImagem(new imageLoader().imageLoader(imgString));
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
    }
    /**
     * @param posicao the posicao to set
     */
}
