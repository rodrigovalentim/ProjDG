package model;

import java.awt.Color;

public abstract class Pedra {
    private int id;
    private Color cor;
    private int[] posicao;

    public Pedra(int id, Color cor){
        setId(id);
        setCor(cor);
        posicao = new int[2];
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Color getCor(){
        return cor;
    }

    public void setCor(Color cor){
        this.cor = cor;
    }

    public int[] getPosicao(){
        return posicao;
    }

    public void setPosicao(int[] posicao) {
        this.posicao = posicao;
    }

    public abstract String identificaPedra();

    /**
     * @param posicao the posicao to set
     */

}
