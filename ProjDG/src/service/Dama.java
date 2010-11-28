package service;

import java.awt.Color;
import model.Pedra;

public class Dama extends Pedra{

    public Dama(int idOwner, int idPedra, Color cor, String imgString){
        super(idOwner, idPedra, cor, imgString);
    }

    public String identificaPedra(){
        return "dama";
    }
}
