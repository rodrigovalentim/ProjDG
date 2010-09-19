package service;

import java.awt.Color;
import model.Pedra;

public class Peca extends Pedra{

    public Peca(int idOwner, int idPedra, Color cor){
        super(idOwner, idPedra, cor);
    }

    public String identificaPedra(){
        return "peca";
    }
    
}
