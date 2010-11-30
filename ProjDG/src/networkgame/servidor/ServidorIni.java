/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkgame.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Davi Sande
 */

//Thread que cria o socket do servidor e realiza a comunicação com cliente
public class ServidorIni implements Runnable {
    
    private ServerSocket socketServidor;
    private Socket socketCliente;
    private Vector clientes;
    private int porta;
    private String mensagem;

    //Contrutor recebe a porta a ser utilizada pelo socket
    public ServidorIni(int porta) {
        clientes = new Vector();
        this.porta = porta;
    }

    /* Thread cria o socket e aguarda a conexão no cliente. Quando o cliente uma nova thread
       é iniciada para efetuar comunicação com o cliente. */
    public void run() {
        try {
            socketServidor = new ServerSocket(porta);
            while (true) {
                new Thread(new ComunicacaoCliente(socketServidor.accept())).start();
            }
        } catch (IOException ex) {
            System.out.println("Erro ao criar socket do servidor: \n" + ex);
        }
    }

    /* Thread que cria os canais de comunicação com cliente, armazenando todos os de saída em um vetor,
       a fim de enviar as mensagens recebidas para todos os clientes conectados. */
    public class ComunicacaoCliente implements Runnable {

        public ComunicacaoCliente(Socket socket) {
            socketCliente = socket;
        }

        public void run() {
            try {
                DataInputStream in = new DataInputStream(socketCliente.getInputStream());
                DataOutputStream out = new DataOutputStream(socketCliente.getOutputStream());
                clientes.add(out);
                while (true) {
                    mensagem = in.readUTF();
                    mensagemParaTodos(out, mensagem);
                }
            } catch (IOException ex) {
                System.out.println("Erro na comunicacao com o cliente: \n" + ex);
            }
        }
    }

    //Método que envia mensagem a todos o clentes conectados
    public void mensagemParaTodos(DataOutputStream out, String mensagem) throws IOException {
        Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {
            DataOutputStream saida = (DataOutputStream) e.nextElement();
            if(out != saida){
                saida.writeUTF(mensagem);
            }
        }
    }

    //Método que fecha os canais de saída
    public void desconectar() throws IOException {
        Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {
            DataOutputStream saida = (DataOutputStream) e.nextElement();
            saida.close();
        }
    }
}
