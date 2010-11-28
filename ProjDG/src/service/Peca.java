package service;

import java.awt.Color;
import model.Pedra;

public class Peca extends Pedra{

    public Peca(int idOwner, int idPedra, Color cor, String imgString){
        super(idOwner, idPedra, cor, imgString);
    }

    public String identificaPedra(){
        return "peca";
    }
    
}
