package service;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import utils.ImagemLoad;
import utils.ToImageIcon;

public class Placar extends JInternalFrame {

    private Jogador jogador1;
    private Jogador jogador2;
    private JTextArea resultado;
    private String mensagem;
    private String placarImagem = "imagem/placar_imagem.png";
    private BufferedImage imagem;
    private JPanel p1, p2;
    private JLabel img2;

    public Placar(Jogador jogador1, Jogador jogador2) {
        setJogador1(jogador1);
        setJogador2(jogador2);
        setBounds(600, 0, 170, 400);
        setBackground(new Color(0, 0, 0, 0));
        resultado = new JTextArea();
        atualizarPlacar();
        mensagem = "";
        imagem = new ImagemLoad().imageLoader(placarImagem);
        setP1(new JPanel(new GridLayout(1, 24)));
        getP1().setBounds(600, 10, 10, 10);
        getP1().setBounds(600, 400, 10, 10);
        getP1().setBackground(new Color(0,0,0,0));
        img2 = new JLabel(new ToImageIcon().toImageIcon(new ImagemLoad().imageLoader("imagem/pedraclaramini.png")));
        getP1().add(img2,0);
        getP1().add(new JLabel(new ToImageIcon().toImageIcon(new ImagemLoad().imageLoader("imagem/pedraclaramini.png"))),10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.add(getP1());
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.drawImage(imagem, 0, 0, this);
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null); //retirar o painel superior
        setBorder(null); //removendo borda
        
    }

    /*
     * Metodo mostra torna possivel exibir este objeto grafico no frame principal.
     */
    public void mostra(JDesktopPane main) {
        setOpaque(false);
        main.add(this);
        show();
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    private void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    private void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }

    public void verificaVencedor(Component parent) {
        if (jogador1.getPontos() == 12) {
            JOptionPane.showMessageDialog(parent, "Parabens ao jogador " + getJogador1().getNome() + "!!\n" + "Comeu Todas as Pecas do jogador " + getJogador2().getNome());
        }
        if (jogador2.getPontos() == 12) {
            JOptionPane.showMessageDialog(parent, "Parabens ao jogador " + getJogador2().getNome() + "!!\n" + "Comeu Todas as Pecas do jogador " + getJogador1().getNome());
        }
    }

    public void atualizarPlacar() {
        resultado.setText(jogador1.getNome() + " = " + jogador1.getPontos() + "\n" + jogador2.getNome() + " = " + jogador2.getPontos());
    }

    /**
     * @return the p1
     */
    public JPanel getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(JPanel p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public JPanel getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(JPanel p2) {
        this.p2 = p2;
    }
}
