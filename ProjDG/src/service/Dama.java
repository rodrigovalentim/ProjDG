package service;

import java.awt.Color;
import model.Pedra;

public class Dama extends Pedra{

    public Dama(int id, Color cor){
        super(id, cor);
    }

    public String identificaPedra(){
        return "dama";
    }

}
