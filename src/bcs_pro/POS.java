/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bcs_pro;

import javax.swing.JScrollPane;

/**
 *
 * @author LANTAI3
 */
public class POS extends javax.swing.JInternalFrame {

    /**
     * Creates new form POS
     */
    private PesananPenjualan2 PanelMetode3 = new PesananPenjualan2();
    public POS() {
        initComponents();
        JScrollPane scrollPane3 = new JScrollPane(PanelMetode3);
        jTabbedPane1.addTab("Penjualan Pesanan", PanelMetode3);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("POS");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/GambarKecil/logo.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
