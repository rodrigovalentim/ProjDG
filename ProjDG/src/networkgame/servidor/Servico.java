/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networkgame.servidor;

import exception.FilaVaziaException;
import java.io.IOException;

/**
 *
 * @author Davi
 */
public class Servico {
    private TelaServidor telaServidor;
    private Servidor servidor;
    private String mensagem;
    

    public Servico(boolean Screen){
        telaServidor = new TelaServidor(this);
        telaServidor.setVisible(Screen);
        servidor = new Servidor(1235);
        this.mensagem = "";
        exibeMensagem("Servidor iniciado na porta 1235.");
        
    }

    public void iniciaServidor(){
        servidor.iniciaServidor();
    }

    public void exibeMensagem(String mensagem){
        this.mensagem += mensagem + "\n";
        telaServidor.exibeMensagem(this.mensagem);
    }

    public void desconecta() throws IOException, FilaVaziaException{
        servidor.desconectar();
        exibeMensagem("Servidor desconectado.");
    }

}
