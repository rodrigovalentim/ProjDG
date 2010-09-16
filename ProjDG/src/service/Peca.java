package service;

import java.awt.Color;
import model.Pedra;

public class Peca extends Pedra{

    public Peca(int id, Color cor){
        super(id, cor);
    }

    public String identificaPedra(){
        return "peca";
    }
    
}
