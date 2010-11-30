package networkgame.clienteserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Davi Sande
 */
public class Cliente {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    String mensagem;
    ServicoCliente servicoCliente;

    public Cliente(ServicoCliente servicoCliente) {
        mensagem = "";
        this.servicoCliente = servicoCliente;
    }

    public void iniciaCliente(String ip, int porta) throws UnknownHostException, IOException {
        socket = new Socket(ip, porta);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(new CapturaMensagem()).start();
    }

    public void desconectar() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void enviaMensagem(String mensagem) throws IOException {
        out.writeUTF(mensagem);
    }

    public class CapturaMensagem implements Runnable {

        public void run() {
            while (true) {
                try {
                    mensagem = in.readUTF();
                } catch (IOException ex) {
                    System.out.println("Erro ao capturar mensagem do servidor: \n" + ex);
                }
                servicoCliente.capturaMensagem(mensagem);
            }
        }
    }
}