package service;

import java.util.ArrayList;
import model.Pedra;

public class Jogador {
    private int id;
    private String nome;
    private ArrayList<Pedra> pedras;
    private int pontos;
    private int posicaoInicial;

    public Jogador(String nome, int idJogador, int pos){
        setNome(nome);
        setId(idJogador);
        pedras = new ArrayList<Pedra>();
        setPontos(0);
        setPosicaoInicial(pos);
        System.out.println("Jogador "+getNome()+" Posicao inicial "+pos);
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public ArrayList<Pedra> getPedras(){
        return pedras;
    }
    
    public void addPedra(Pedra pedra){
        pedras.add(pedra);
    }

    public void removePedra(int idx){
        pedras.remove(idx);
    }

    public int getPontos(){
        return pontos;
    }

    public void setPontos(int valor){
        pontos = valor;
    }

    public void addPontos(){
        pontos++;
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

    /**
     * @return the posicaoInicial
     */
    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    /**
     * @param posicaoInicial the posicaoInicial to set
     */
    public void setPosicaoInicial(int posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }
}
