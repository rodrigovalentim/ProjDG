package service;

import java.awt.Color;
import model.Pedra;
import utils.ImagemLoad;

public class Peca extends Pedra {

    private static String pecaClara = "imagem/pedraclara.png";
    private static String pecaEscura = "imagem/pedraescura.png";

    public Peca(int idOwner, int idPedra, Color cor) {
        super(idOwner, idPedra, cor);
    }

    public String identificaPedra() {
        return "peca";
    }

    @Override
    public void insereImagem() {
        if (getIdOwner() == 0) {
            setImagem(new ImagemLoad().imageLoader(pecaClara));
        } else {
            setImagem(new ImagemLoad().imageLoader(pecaEscura));
        }
    }
}
