package br.com.view;

import br.com.bean.Ticket;
import br.com.bean.enumeration.StatusTicket;
import br.com.dao.TicketDAO;
import br.com.util.TicketTableModel;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Eduardo Hernandorena
 */
public class FormEstorno extends javax.swing.JDialog {

    private TicketTableModel model = new TicketTableModel();
    private TelaPrincipal princ;

    public FormEstorno(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        princ = (TelaPrincipal) parent;
        princ.evt.setWin(this);
        initTable(null);
    }

    public void initTable(List<Ticket> list) {
        if (list == null) {
            list = new TicketDAO().findAllFechados();
        }
        //cria o modelo de NFe da tabela
        model = new TicketTableModel(list);

        //atribui o modelo Ã  tabela
        tbTicket.setModel(model);
        tbTicket.setAutoCreateRowSorter(true);
        txtTotal.setText(String.valueOf(model.getRowCount()));
        ajustarColumns();
    }

    private void ajustarColumns() {
        TableColumnModel colModel = tbTicket.getColumnModel();

        //codigo
        colModel.getColumn(0).setWidth(50);
        colModel.getColumn(0).setCellRenderer(alinhar(TelaPrincipal.Align.RIGHT));
        colModel.getColumn(0).setResizable(false);

        //Placa
        colModel.getColumn(1).setWidth(100);
        colModel.getColumn(1).setCellRenderer(alinhar(TelaPrincipal.Align.CENTER));
        colModel.getColumn(1).setResizable(false);

        //modelo
        colModel.getColumn(2).setWidth(50);
        colModel.getColumn(2).setCellRenderer(alinhar(TelaPrincipal.Align.CENTER));
        colModel.getColumn(2).setResizable(false);

        //entrada
        colModel.getColumn(3).setWidth(100);
        colModel.getColumn(3).setCellRenderer(alinhar(TelaPrincipal.Align.CENTER));
        colModel.getColumn(3).setResizable(false);
    }

    private DefaultTableCellRenderer alinhar(TelaPrincipal.Align align) {
        DefaultTableCellRenderer alinha = new DefaultTableCellRenderer();
        if (TelaPrincipal.Align.LEFT == align) {
            alinha.setHorizontalAlignment(SwingConstants.LEFT);
        } else if (TelaPrincipal.Align.CENTER == align) {
            alinha.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (TelaPrincipal.Align.RIGHT == align) {
            alinha.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return alinha;
    }

    public String backSpace(String texto, KeyEvent ke) {
        if (txtPlaca.getText().isEmpty() || txtPlaca.getText().length() == 1) {
            txtPlaca.setText("");
            initTable(null);
            return "";
        } else {
            texto = texto.substring(0, texto.length() - 1);
            return defaultAct(texto, null);
        }
    }

    public String defaultAct(String texto, KeyEvent ke) {
        if (!txtPlaca.isFocusOwner()) {
            txtPlaca.requestFocus();
        }
        txtPlaca.setText("");
        if (ke != null) {
            texto += ke.getKeyChar();
        }
        txtPlaca.setText(texto);

        initTable(model.getTickets(texto));

        return texto;
    }

    public void estornaTicket() {
        if (tbTicket.getSelectedRow() != -1) {
//            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja estornar este cupom?", "Estorno", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Ticket ticket = model.getTicket(tbTicket.getSelectedRow());
            ticket.setHoraSai(null);
            ticket.setDataSai(null);
            ticket.setValor(0d);
            ticket.setTempo(null);
            ticket.setStatus(StatusTicket.ATIVO);
            new TicketDAO().upDate(ticket);
            initTable(null);
            princ.initTable(null);
//            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbTicket = new javax.swing.JTable();
        txtPlaca = new javax.swing.JTextField();
        txtTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Estorno de Ticket");

        tbTicket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbTicket);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(835, 515));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tbTicket;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
