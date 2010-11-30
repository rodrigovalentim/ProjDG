package service;

import estruturas.Pilha;
import exception.PilhaVaziaException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import utils.ImagemLoad;
import utils.ToImageIcon;

public class Placar extends JInternalFrame {

    private Jogador jogador1;
    private Jogador jogador2;
    private String placarImagem = "imagem/placar_imagem.png";
    private BufferedImage imagem;
    private JPanel painelPlacar;
    private JLabel img;
    private Pilha pilhaPlayer1, pilhaPlayer2;

    public Placar(Jogador jogador1, Jogador jogador2) {
        setJogador1(jogador1);
        setJogador2(jogador2);
        pilhaPlayer1 = new Pilha();
        pilhaPlayer2 = new Pilha();
        populaPilhas();
        setBounds(600, 0, 170, 400);
        imagem = new ImagemLoad().imageLoader(placarImagem);
        setBackground(new Color(0, 0, 0, 0)); //fundo transparente
        painelPlacar = new JPanel();
        painelPlacar.setLayout(null);
        painelPlacar.setBackground(new Color(0, 0, 0, 0)); //fundo transparente
        add(painelPlacar);
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

    public void atualizarPlacar() throws PilhaVaziaException {
        if (getJogador2().getPontos() > 0) {
            System.out.println("getjogador2");
            img = new JLabel(new ToImageIcon().toImageIcon(new ImagemLoad().imageLoader((String) pilhaPlayer1.desempilhar())));
            getPainelPlacar().add(img);
            if (getJogador2().getPontos() <= 6) {
                img.setBounds(2, getJogador2().getPontos() * 20, 20, 20);
            } else {
                if (getJogador2().getPontos() >= 7) {
                    img.setBounds(22, (getJogador2().getPontos() - 6) * 20, 20, 20);
                }
            }
        } else {
            System.out.println("getjogador1");
            img = new JLabel(new ToImageIcon().toImageIcon(new ImagemLoad().imageLoader((String) pilhaPlayer2.desempilhar())));
            getPainelPlacar().add(img);
            if (getJogador1().getPontos() <= 6) {
                img.setBounds(2, (getJogador1().getPontos() * 200), 20, 20);
            } else {
                if (getJogador1().getPontos() >= 7) {
                    img.setBounds(22, ((getJogador1().getPontos() - 6) * 10) * 2, 20, 20);
                }
            }
        }
    }

    private void populaPilhas() {
        for (int a = 12; a >= 0; a--) {
            pilhaPlayer1.empilhar("imagem/pedraclaramini.png");
            pilhaPlayer2.empilhar("imagem/pedraescuramini.png");
        }
    }

    /**
     * @return the painelPlacar
     */
    public JPanel getPainelPlacar() {
        return painelPlacar;
    }

    /**
     * @param painelPlacar the painelPlacar to set
     */
    public void setPainelPlacar(JPanel painelPlacar) {
        this.painelPlacar = painelPlacar;
    }
}
