package br.com.view;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TelaPrincipal extends javax.swing.JFrame {

    public TelaPrincipal() {
        initComponents();
        ajustarColumns();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbTicket = new javax.swing.JTable();
        mbMenus = new javax.swing.JMenuBar();
        mnCad = new javax.swing.JMenu();
        mnExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FreePark - Gerenciador de Estacionamentos");
        setPreferredSize(new java.awt.Dimension(1024, 768));
        setResizable(false);

        tbTicket.setAutoCreateRowSorter(true);
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
        tbTicket.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbTicket.setFocusCycleRoot(true);
        jScrollPane1.setViewportView(tbTicket);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ajustarColumns() {
        TableColumnModel colModel = tbTicket.getColumnModel();

        //codigo
        colModel.getColumn(0).setPreferredWidth(50);
        colModel.getColumn(0).setCellRenderer(alinhar(Align.RIGHT));
        colModel.getColumn(0).setResizable(false);

        //Placa
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(1).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(1).setResizable(false);
        
        //modelo
        colModel.getColumn(2).setPreferredWidth(50);
        colModel.getColumn(2).setCellRenderer(alinhar(Align.CENTER));
        colModel.getColumn(2).setResizable(false);
        
        //entrada
        colModel.getColumn(3).setPreferredWidth(100);
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

    public enum Align {

        LEFT, CENTER, RIGHT;
    }

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar mbMenus;
    private javax.swing.JMenu mnCad;
    private javax.swing.JMenu mnExit;
    private javax.swing.JTable tbTicket;
    // End of variables declaration//GEN-END:variables
}
