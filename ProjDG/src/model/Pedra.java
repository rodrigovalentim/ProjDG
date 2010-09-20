package model;

import java.awt.Color;

public abstract class Pedra {
    private int idOwner;
    private int idPedra;
    private Color cor;

    public Pedra(int idowner, int idPedra, Color cor){
        setIdOwner(idowner);
        setIdPedra(idPedra);
        setCor(cor);
    }

    public int getIdOwner(){
        return idOwner;
    }

    public void setIdOwner(int id){
        this.idOwner = id;
    }

    public Color getCor(){
        return cor;
    }

    public void setCor(Color cor){
        this.cor = cor;
    }

//    public int[] getPosicao(){
//        return posicao;
//    }

//    public void setPosicao(int[] posicao) {
//        this.posicao = posicao;
//    }

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
     * @param posicao the posicao to set
     */

}
