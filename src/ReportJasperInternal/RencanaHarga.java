/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportJasperInternal;

import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author LANTAI3
 */
public class RencanaHarga extends javax.swing.JInternalFrame {

    /**
     * Creates new form RencanaHarga
     */
    public RencanaHarga(JRViewer vw) {
        initComponents();
        getContentPane().add(vw);

        vw.setBounds(5, 5, 800, 900);
        setVisible(true);
        setSize(900, 650);
    }
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
