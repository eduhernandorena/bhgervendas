package br.com.view;

import br.com.dao.RelatorioDAO;
import br.com.util.Service;
import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Eduardo Hernandorena
 */
public class FormRel extends javax.swing.JDialog {

    MaskFormatter mascara;
    MaskFormatter mascaraH;

    public FormRel(TelaPrincipal princ) {
        super(princ, true);
        try {
            mascara = new MaskFormatter("##/##/##");
            mascaraH = new MaskFormatter("##:##");
            initComponents();
            HashSet backup = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
            HashSet conj = (HashSet) backup.clone();
            conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
            this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
            princ.evt.setWin(this);
            fillInicial();
        } catch (ParseException ex) {
            Logger.getLogger(FormRel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillInicial() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        rdSintetico.setSelected(true);
        txtDataInicial.setText(sdf.format(new Date()));
        txtDataFinal.setText(sdf.format(new Date()));
        txtHoraInicial.setText("08:00");
        txtHoraFinal.setText("21:00");
    }

    public void lancaRel() {
        if (valida()) {
            Date ini = new Date();
            Date fim = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
            String tipo = rdSintetico.isSelected() ? "Sintético" : "Analítico";
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja emitir o relatório " + tipo, "Emitir Relatório", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    ini = sdf.parse(txtDataInicial.getText() + " " + txtHoraInicial.getText());
                    fim = sdf.parse(txtDataFinal.getText() + " " + txtHoraFinal.getText());
                    new RelatorioDAO().getSintetico(ini, fim);
                } catch (ParseException ex) {
                    Logger.getLogger(FormRel.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rdSintetico.isSelected()) {
                    new RelatorioDAO().getSintetico(ini, fim);
                } else {
                    new RelatorioDAO().getAnalitico(ini, fim);
                }
                this.dispose();
            }
        }
    }

    private boolean valida() {
        if (txtHoraFinal.isFocusOwner()) {
            if (txtDataInicial.getText() == null || txtDataInicial.getText().trim().isEmpty()) {
                txtDataInicial.requestFocus();
                Service.message("Data Inicial", "Para Emitir o relatório favor preencher todos os campos.", 0);
                return false;
            }
            if (txtDataFinal.getText() == null || txtDataFinal.getText().trim().isEmpty()) {
                txtDataFinal.requestFocus();
                Service.message("Data Final", "Para Emitir o relatório favor preencher todos os campos.", 0);
                return false;
            }
            if (txtHoraInicial.getText() == null || txtHoraInicial.getText().trim().isEmpty()) {
                txtHoraInicial.requestFocus();
                Service.message("Hora Inical", "Para Emitir o relatório favor preencher todos os campos.", 0);
                return false;
            }
            if (txtHoraFinal.getText() == null || txtHoraFinal.getText().trim().isEmpty()) {
                txtHoraFinal.requestFocus();
                Service.message("Hora Final", "Para Emitir o relatório favor preencher todos os campos.", 0);
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rdAnalitico = new javax.swing.JRadioButton();
        rdSintetico = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtDataInicial = new javax.swing.JFormattedTextField();
        txtDataFinal = new javax.swing.JFormattedTextField();
        txtHoraInicial = new javax.swing.JFormattedTextField();
        txtHoraFinal = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatórios");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(153, 153, 153)));
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        buttonGroup1.add(rdAnalitico);
        rdAnalitico.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdAnalitico.setText("Analítico");

        buttonGroup1.add(rdSintetico);
        rdSintetico.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdSintetico.setText("Sintético");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdAnalitico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(rdSintetico)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdSintetico)
                    .addComponent(rdAnalitico))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtDataInicial.setColumns(1);
        txtDataInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtDataInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataInicial.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtDataFinal.setColumns(1);
        txtDataFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        txtDataFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataFinal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtHoraInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        txtHoraInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoraInicial.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtHoraFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        txtHoraFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoraFinal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Inicial:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Final:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDataFinal, txtDataInicial, txtHoraFinal, txtHoraInicial});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(txtHoraInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(txtHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtDataFinal, txtDataInicial, txtHoraFinal, txtHoraInicial});

        setSize(new java.awt.Dimension(337, 213));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rdAnalitico;
    private javax.swing.JRadioButton rdSintetico;
    private javax.swing.JFormattedTextField txtDataFinal;
    private javax.swing.JFormattedTextField txtDataInicial;
    private javax.swing.JFormattedTextField txtHoraFinal;
    private javax.swing.JFormattedTextField txtHoraInicial;
    // End of variables declaration//GEN-END:variables
}
