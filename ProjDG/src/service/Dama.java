package service;

import java.awt.Color;
import model.Pedra;
import utils.ImagemLoad;

public class Dama extends Pedra {

    private static String pecaQueenClara = "imagem/pedraqueenclara.png";
    private static String pecaQueenEscura = "imagem/pedraqueenescura.png";

    public Dama(int idOwner, int idPedra, Color cor) {
        super(idOwner, idPedra, cor);
    }

    public String identificaPedra() {
        return "dama";
    }

    @Override
    public void insereImagem() {
        if (getIdOwner() == 0) {
            setImagem(new ImagemLoad().imageLoader(pecaQueenClara));
        } else {
            setImagem(new ImagemLoad().imageLoader(pecaQueenEscura));
        }
    }
}
