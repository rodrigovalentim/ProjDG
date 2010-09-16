package service;

public class Placar {

    private Jogador jogador1;
    private Jogador jogador2;

    public Placar(Jogador jogador1, Jogador jogador2){
        setJogador1(jogador1);
        setJogador2(jogador2);
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    private void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    private void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public void atualizarPlacar(){
        System.out.println(jogador1.getNome() + " = " + jogador1.getPontos() + "\n" + jogador2.getNome() + " = " + jogador2.getPontos());
    }



}
