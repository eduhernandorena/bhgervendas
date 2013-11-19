package br.com.view;

import br.com.bean.Ticket;
import br.com.util.Service;
import java.text.SimpleDateFormat;

public class FormFechaTicket extends javax.swing.JDialog {

    public Ticket tick = null;

    public FormFechaTicket(TelaPrincipal princ, Ticket t) {
        super(princ, true);
        initComponents();
        princ.evt.setWin(this);
        fillTicket(t);
    }

    private void fillTicket(Ticket ticket) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfH = new SimpleDateFormat("HH:mm:ss");
        txtDataEntrada.setText(sdf.format(ticket.getDataEnt()));
        txtHrEntrada.setText(sdfH.format(ticket.getHoraEnt()));
        txtDataSaida.setText(sdf.format(ticket.getDataSai()));
        txtHrSaida.setText(sdfH.format(ticket.getHoraSai()));
        txtMod.setText(String.valueOf(ticket.getTabela().getMod().ordinal()));
        txtModDesc.setText(ticket.getTabela().getMod().name());
        txtTotalTempo.setText(ticket.getTempo());
        txtTotalValor.setText(ticket.getValor().toString());
        tick = ticket;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDataEntrada = new javax.swing.JFormattedTextField();
        txtTotalValor = new javax.swing.JFormattedTextField();
        txtHrEntrada = new javax.swing.JFormattedTextField();
        txtHrSaida = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDataSaida = new javax.swing.JFormattedTextField();
        txtTotalTempo = new javax.swing.JTextField();
        txtModDesc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fechamento de Ticket");

        txtDataEntrada.setEditable(false);
        txtDataEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDataEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtDataEntrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtDataEntrada.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtDataEntrada.setEnabled(false);
        txtDataEntrada.setFocusable(false);
        txtDataEntrada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDataEntrada.setPreferredSize(new java.awt.Dimension(55, 28));

        txtTotalValor.setEditable(false);
        txtTotalValor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        txtTotalValor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtTotalValor.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtTotalValor.setEnabled(false);
        txtTotalValor.setFocusable(false);
        txtTotalValor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotalValor.setMaximumSize(new java.awt.Dimension(55, 28));
        txtTotalValor.setMinimumSize(new java.awt.Dimension(55, 28));
        txtTotalValor.setPreferredSize(new java.awt.Dimension(55, 28));

        txtHrEntrada.setEditable(false);
        txtHrEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHrEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        txtHrEntrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHrEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtHrEntrada.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtHrEntrada.setEnabled(false);
        txtHrEntrada.setFocusable(false);
        txtHrEntrada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtHrSaida.setEditable(false);
        txtHrSaida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtHrSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        txtHrSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHrSaida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtHrSaida.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtHrSaida.setEnabled(false);
        txtHrSaida.setFocusable(false);
        txtHrSaida.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Entrada:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Saída:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Modalidade:");

        txtMod.setEditable(false);
        txtMod.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMod.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtMod.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtMod.setEnabled(false);
        txtMod.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Valor Total (R$):");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tempo Total:");

        txtDataSaida.setEditable(false);
        txtDataSaida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtDataSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        txtDataSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataSaida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtDataSaida.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtDataSaida.setEnabled(false);
        txtDataSaida.setFocusable(false);
        txtDataSaida.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDataSaida.setMaximumSize(new java.awt.Dimension(55, 28));
        txtDataSaida.setMinimumSize(new java.awt.Dimension(55, 28));
        txtDataSaida.setPreferredSize(new java.awt.Dimension(55, 28));

        txtTotalTempo.setEditable(false);
        txtTotalTempo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTotalTempo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTotalTempo.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtTotalTempo.setEnabled(false);
        txtTotalTempo.setFocusable(false);

        txtModDesc.setEditable(false);
        txtModDesc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtModDesc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtModDesc.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtModDesc.setEnabled(false);
        txtModDesc.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMod, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModDesc))
                    .addComponent(txtTotalTempo)
                    .addComponent(txtTotalValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDataSaida, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(txtDataEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHrSaida, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(txtHrEntrada))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtMod, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtModDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHrEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHrSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTotalValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTotalTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(404, 270));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JFormattedTextField txtDataEntrada;
    private javax.swing.JFormattedTextField txtDataSaida;
    private javax.swing.JFormattedTextField txtHrEntrada;
    private javax.swing.JFormattedTextField txtHrSaida;
    private javax.swing.JTextField txtMod;
    private javax.swing.JTextField txtModDesc;
    private javax.swing.JTextField txtTotalTempo;
    private javax.swing.JFormattedTextField txtTotalValor;
    // End of variables declaration//GEN-END:variables
}
