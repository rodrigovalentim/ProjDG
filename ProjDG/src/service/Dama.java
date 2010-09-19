package service;

import java.awt.Color;
import model.Pedra;

public class Dama extends Pedra{

    public Dama(int idOwner, int idPedra, Color cor){
        super(idOwner, idPedra, cor);
    }

    public String identificaPedra(){
        return "dama";
    }
}
