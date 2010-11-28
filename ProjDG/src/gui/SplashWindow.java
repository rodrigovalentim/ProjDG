package gui;

/**
 *
 * @author Rodrigo
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import utils.ImageLoader;
import utils.toImage;
import utils.toImageIcon;

/**
 * Esta classe gerencia uma tela de splash.
 *
 * @author Rodrigo Valentim
 * @version 27/11/2010
 */

public class SplashWindow extends JWindow implements WindowListener
{
    private JLabel imagem = null;
    private ImageIcon figura = null;

    /**
     * Construtor, rebece uma String com o endereco da imagem que sera exibida na tela de splash.
     * @param splash    String com o endereco da imagem exibida na tela de splash.
     */
    public SplashWindow(String img) {
        imagem = new JLabel(new toImageIcon().toImageIcon(new ImageLoader().imageLoader(img)));
        imagem.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
    }

    public ImageIcon getFigura() {
        return figura;
    }

    /**
     * Exibe a tela de splash.
     * Recebe um int que informa o tempo de exibicao, em milisegundos, da tela de splash.
     * @param tempo     int que indica o tempo, em milisegundos, que sera exibida a tela de splash.
     * @return void
     */
    public void open( int tempo )
    {
        this.getContentPane().add( imagem );
        this.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation( ( dimension.width - getSize().width )/2, ( dimension.height - getSize().height )/2 );
        this.addWindowListener( this );
        this.setVisible( true );

        sleep( tempo );
    }

    /**
     * Aguarda um tempo em milisegundos
     * @param tempo     int que representa o tempo, em milisegundos, que ser�aguardado.
     * @exception InterruptedException
     * @exception Exception
     * @return void
     */
    private void sleep( int tempo )
    {
        try {
            Thread.sleep( tempo );
            this.setVisible(false);
        }
        catch( InterruptedException ie ) {
            ie.printStackTrace();
            JOptionPane.showMessageDialog( null, " Erro 00 - Falha no sleep do Splash ", " Erro ", JOptionPane.ERROR_MESSAGE );
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, " Erro 00 - Falha no sleep do Splash ", " Erro ", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Fecha a janela de splash
     * @return void
     */
    public void close()
    {
        dispose();
    }

    /**
     * @param windowevent
     * @return void
     */
    public void windowOpened( WindowEvent windowevent )
    {
        Graphics2D graphics2d = (Graphics2D)getGlassPane().getGraphics();
        graphics2d.setColor( new Color( 51, 102, 153 ) );
        graphics2d.setFont( new Font( "SansSerif", 0, 16 ) );
    }
    /**
     * @param windowevent
     * @return void
     */
    public void windowActivated( WindowEvent windowevent )
    {
    }
    /**
     * @param windowevent
     * @return coid
     */
    public void windowClosed( WindowEvent windowevent )
    {
    }
    /**
     * @param windowevent
     * @return void
     */
    public void windowClosing( WindowEvent windowevent )
    {
    }
    /**
     * @param windowevent
     * @return void
     */
    public void windowDeactivated( WindowEvent windowevent )
    {
    }
    /**
     * @param windowevent
     * @return void
     */
    public void windowDeiconified( WindowEvent windowevent )
    {
    }
    /**
     * @param windowevent
     * @return void
     */
    public void windowIconified( WindowEvent windowevent )
    {
    }
}