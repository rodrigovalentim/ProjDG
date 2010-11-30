/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Ueber
 */
import control.Jogo;
import estruturas.Pilha;
import exception.PilhaVaziaException;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import service.Jogador;

public class teste {

    public class Play1 extends JInternalFrame {

        private Container placarContainer;
        private GridBagLayout placarGridBagLayout;
        private GridBagConstraints placarGBConstraints;
        private Jogador jogador1, j1, j2, z;
        private JFrame jframe;
        private Icon icon;
        private JTextArea resultado;
        private JPanel jpanel;
        private JLabel jlabel;
        private Pilha pilha1;
        private String img, dado;
        private Jogo jogo;
        private int x;

        public Play1(Jogador jogador1) throws PilhaVaziaException {
            pilha1 = new Pilha();
            addPilha();
            setJogador(jogador1);
            setBounds(702, 0, 200, 100);
            jpanel = new JPanel();
            icon = new ImageIcon((String) pilha1.desempilhar());
            jlabel = new JLabel(icon);
            jpanel.add(jlabel);
            this.getContentPane().add(jpanel);
            //atualizarPlacar();
            setTitle("Placar Player 1");
        }

        public void addPilha() {
            for (int a = 12; a >= 0; a--) {
                img = String.valueOf(a);
                dado = ("c:/img/img" + img + ".png");
                pilha1.empilhar(dado);
            }
        }

        /*
         * Metodo mostra torna possivel exibir este objeto grafico no frame principal.
         */
        public void mostra(JDesktopPane main) {
            main.add(this);
            setOpaque(true);
            show();
        }

        public Container getPlacarConteiner() {
            return placarContainer;
        }

        public GridBagLayout getPlacarContainer() {
            return placarGridBagLayout;
        }

        public GridBagConstraints getPlacarGBConstraints() {
            return placarGBConstraints;
        }

        public void setPlacarConteiner(Container placarContainer) {
            this.placarContainer = placarContainer;
        }

        public void setPlacarContainer(GridBagLayout placarGridBagLayout) {
            this.placarGridBagLayout = placarGridBagLayout;
        }

        public void setPlacarGBConstraints(GridBagConstraints placarGBConstraints) {
            this.placarGBConstraints = placarGBConstraints;
        }

        public Jogador getJogador() {
            return jogador1;
        }

        private void setJogador(Jogador jogador1) {
            this.jogador1 = jogador1;
        }

        public void verificaVencedor(Component parent) {
            if (pilha1.vazia() == true) {
                JOptionPane.showMessageDialog(parent, "Parabens ao jogador " + getJogador().getNome() + "!!\n" + "Venceu o jogador Player 2 ");
                System.exit(0);
            }
        }

        public void atualizarPlacar() throws PilhaVaziaException {
            if (jogador1.getPontos() > this.x) {
                jpanel.removeAll();
                //System.out.print(pilha1.desempilhar());
                jpanel.add(new JLabel(new ImageIcon((String) pilha1.desempilhar())));
                this.getContentPane().add(jpanel);
                //getContentPane().repaint();
                jpanel.updateUI();
                x++;
            }
        }
    }
}
