package networkgame.servidor;

import exception.FilaVaziaException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class TelaServidor extends javax.swing.JFrame {
    Servico servico;

    /** Creates new form TelaServidor */
    public TelaServidor(Servico servico) {

        //Definindo que a tela irá assumir o design do sistema operacional
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao definir interface: \n" + e);
        }

        initComponents();

        this.servico = servico;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taMensagem = new javax.swing.JTextArea();
        btDesconectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");

        taMensagem.setColumns(20);
        taMensagem.setRows(5);
        jScrollPane1.setViewportView(taMensagem);

        btDesconectar.setText("Desconectar");
        btDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesconectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(btDesconectar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btDesconectar)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesconectarActionPerformed
        try {
            servico.desconecta();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao desconectar o servidor");
        }
        catch(FilaVaziaException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        System.exit(0);
    }//GEN-LAST:event_btDesconectarActionPerformed

    public void exibeMensagem(String mensagem){
        taMensagem.setText(mensagem);
    }

    /**
    * @param args the command line arguments
    */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDesconectar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taMensagem;
    // End of variables declaration//GEN-END:variables

}
