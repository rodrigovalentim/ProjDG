/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import networkgame.clienteserver.ServicoCliente;
import utils.ImagemLoad;

/**
 *
 * @author rodrigo.valentim
 */
public class Chat extends JInternalFrame implements ServicoCliente {

    private final String fundo = "imagem/backgroundFloor.png";
    private BufferedImage imagem;
    private TextArea textArea;

    public Chat() {
        imagem = new ImagemLoad().imageLoader(fundo);
        textArea = new TextArea("", 4, 1, 1);
        setBounds(600, 400, 170, 120);
        setBackground(new Color(0, 0, 0, 0)); //fundo transparente
        add(textArea);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.drawImage(imagem, 0, 0, this);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null); //retirar o painel superior
        setBorder(null); //removendo borda
    }
    /*
     * Exibe esta classe em forma grafica no container principal
     */

    public void mostra(JDesktopPane main) {
        setOpaque(true);
        main.add(this);
        show();
    }

    /**
     * @return the textArea
     */
    public TextArea getTextArea() {
        return textArea;
    }

    /**
     * @param textArea the textArea to set
     */
    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public void capturaMensagem(String mensagem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
