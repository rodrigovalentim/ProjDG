package service;

import estruturas.ListaEncadeada;
import exception.ListaVaziaException;
import model.Pedra;

public class Jogador {
    private int id;
    private String nome;
    private ListaEncadeada pedras;
    private int pontos;
    private int posicaoInicial;

    public Jogador(String nome, int idJogador, int pos){
        setNome(nome);
        setId(idJogador);
        pedras = new ListaEncadeada();
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

    public ListaEncadeada getPedras(){
        return pedras;
    }
    
    public void addPedra(Pedra pedra){
        pedras.adicionar(pedra);
    }

    public void removePedra(int idx) throws ListaVaziaException{
        pedras.remover(idx);
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
