package networkgame.servidor;

import exception.FilaVaziaException;
import java.io.IOException;

public class Servidor {
    private ServidorIni servidorIni;

    public Servidor(int porta){
        servidorIni = new ServidorIni(porta);
    }
    
    public void iniciaServidor(){
        new Thread(servidorIni).start();
    }

    public void desconectar() throws IOException, FilaVaziaException{
        servidorIni.desconectar();
    }
}
