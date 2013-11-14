package br.com.view;

import br.com.bean.Ticket;
import br.com.calc.Calculo;
import br.com.dao.TicketDAO;
import br.com.util.AllKeyIntercept;
import br.com.util.EvtReader;
import br.com.util.TicketTableModel;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TelaPrincipal extends javax.swing.JFrame {

    private static PrintStream ps;
    private static final Logger LOG = Logger.getLogger(TelaPrincipal.class.getName());
    private TicketTableModel model = new TicketTableModel();
    private Ticket ticket;
    public static EvtReader evt;

    public TelaPrincipal() {
        initComponents();
        evt = new EvtReader(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new AllKeyIntercept(evt));
//        logger();
        initTable(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPlaca = new javax.swing.JTextField();
        spGrid = new javax.swing.JScrollPane();
        tbTicket = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        mbMenus = new javax.swing.JMenuBar();
        mnCad = new javax.swing.JMenu();
        mnExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FreePark - Gerenciador de Estacionamentos");
        setPreferredSize(new java.awt.Dimension(1024, 466));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        txtPlaca.setEditable(false);
        txtPlaca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tbTicket.setAutoCreateRowSorter(true);
        tbTicket.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbTicket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbTicket.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbTicket.setAutoscrolls(false);
        tbTicket.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbTicket.setMaximumSize(new java.awt.Dimension(700, 64));
        tbTicket.setShowHorizontalLines(false);
        tbTicket.setShowVerticalLines(false);
        tbTicket.getTableHeader().setReorderingAllowed(false);
        spGrid.setViewportView(tbTicket);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Total de Veículos:");

        txtTotal.setBackground(new java.awt.Color(240, 240, 240));
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setBorder(null);
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.setEnabled(false);
        txtTotal.setFocusable(false);

        mnCad.setText("Cadastros");
        mbMenus.add(mnCad);

        mnExit.setText("Sair");
        mbMenus.add(mnExit);

        setJMenuBar(mbMenus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    public void initTable(List<Ticket> list) {
        if (list == null) {
            list = new TicketDAO().findAll();
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
        colModel.getColumn(0).setCellRenderer(alinhar(Align.RIGHT));
        colModel.getColumn(0).setResizable(false);

        //Placa
        colModel.getColumn(1).setWidth(100);
        colModel.getColumn(1).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(1).setResizable(false);

        //modelo
        colModel.getColumn(2).setWidth(50);
        colModel.getColumn(2).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(2).setResizable(false);

        //entrada
        colModel.getColumn(3).setWidth(100);
        colModel.getColumn(3).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(3).setResizable(false);
    }

    private DefaultTableCellRenderer alinhar(Align align) {
        DefaultTableCellRenderer alinha = new DefaultTableCellRenderer();
        if (Align.LEFT == align) {
            alinha.setHorizontalAlignment(SwingConstants.LEFT);
        } else if (Align.CENTER == align) {
            alinha.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (Align.RIGHT == align) {
            alinha.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return alinha;
    }

    public Ticket fechaTick() {
        if (tbTicket.getSelectedRow() != -1) {
            ticket = model.getTicket(tbTicket.getSelectedRow());
            ticket.setDataSai(new Date());
            ticket.setHoraSai(new Date());
            ticket = Calculo.fechaTicket(ticket);
            return ticket;
        }
        return null;
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

    private void logger() {
        try {
            File file = new File("logPark.txt");
            if (file.exists()) {
                BigDecimal mbTam;
                BigDecimal tamanho = new BigDecimal(file.length());
                System.out.println(tamanho);
                mbTam = tamanho.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_EVEN);
                if (mbTam.longValue() > 10) {
                    file.delete();
                } else {

                    System.out.println(MessageFormat.format("{0}Mb", mbTam.toPlainString()));
                }
            }
            FileOutputStream arquivoLog = new FileOutputStream("logNFe.txt", true);
            ps = new PrintStream(arquivoLog);
            System.setOut(ps);
            System.setErr(ps);
            System.out.println(MessageFormat.format("\nSO: {0} -- Java: {1} -- Data: {2}", System.getProperty("os.name"), System.getProperty("java.runtime.version"), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public enum Align {

        LEFT, CENTER, RIGHT;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar mbMenus;
    private javax.swing.JMenu mnCad;
    private javax.swing.JMenu mnExit;
    private javax.swing.JScrollPane spGrid;
    public javax.swing.JTable tbTicket;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
