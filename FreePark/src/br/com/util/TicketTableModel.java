package br.com.util;

import br.com.bean.Ticket;
import br.com.bean.enumeration.Modalidade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author eduardo
 */
public class TicketTableModel extends AbstractTableModel {

    //constantes que vão representar as colunas
    //(só para facilitar o entendimento do código)
    private final int COL_COD = 0;
    private final int COL_PLACA = 1;
    private final int COL_MODELO = 2;
    private final int COL_ENTRADA = 3;
    //lista dos produtos que serão exibidos
    private List<Ticket> listTicket;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

    public TicketTableModel() {
        listTicket = new ArrayList<>();
    }

    public TicketTableModel(List<Ticket> lista) {
        this();
        listTicket.addAll(lista);
        fireTableDataChanged();
    }

    public List<Ticket> getListTicket() {
        return listTicket;
    }

    @Override
    public int getRowCount() {
        //quantidade de linhas
        //cada produto na lista será uma linha
        return listTicket.size();
    }

    @Override
    public int getColumnCount() {
        //quantidade de colunas
        //vamos exibir só Nome e Quantidade, então são 2 colunas
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        //qual o nome da coluna
        if (column == COL_COD) {
            return "Cod";
        } else if (column == COL_PLACA) {
            return "Placa";
        } else if (column == COL_MODELO) {
            return "Modelo";
        } else if (column == COL_ENTRADA) {
            return "Entrada";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //retorna a classe que representa a coluna
        if (columnIndex == COL_COD) {
            return Long.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //pega o produto da linha
        Ticket ticket = listTicket.get(rowIndex);

        //verifica qual valor deve ser retornado
        if (columnIndex == COL_COD) {
            return ticket.getId();
        } else if (columnIndex == COL_PLACA) {
            return ticket.getPlaca();
        } else if (columnIndex == COL_MODELO) {
            return ticket.getTabela().getMod().name();
        } else if (columnIndex == COL_ENTRADA) {
            return sdf.format(ticket.getDataEnt()) + "     " + sdfHora.format(ticket.getHoraEnt());
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //pega o produto da linha
        Ticket nfe = listTicket.get(rowIndex);

        //verifica qual valor vai ser alterado
        if (columnIndex == COL_COD) {
            nfe.setId(Long.valueOf(aValue.toString()));
        } else if (columnIndex == COL_PLACA) {
            nfe.setPlaca(aValue.toString());
        } else if (columnIndex == COL_MODELO) {
            nfe.getTabela().setMod(Modalidade.valueOf(aValue.toString()));
        } else if (columnIndex == COL_ENTRADA) {
            try {
                nfe.setDataEnt(sdf.parse(aValue.toString().substring(0, 10)));
                nfe.setHoraEnt(sdfHora.parse(aValue.toString().substring(11, 16)));
            } catch (ParseException ex) {
                Logger.getLogger(TicketTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //avisa que os dados mudaram
        fireTableDataChanged();
    }

    //-----------------------------------------------------
    //Métodos personalizados
    //-----------------------------------------------------
    public void inserir(Ticket ticket) {
        listTicket.add(ticket);

        fireTableDataChanged();
    }

    public void excluir(int pos) {
        listTicket.remove(pos);

        fireTableDataChanged();
    }

    public void excluir(Ticket ticket) {
        listTicket.remove(ticket);

        fireTableDataChanged();
    }

    public Ticket getTicket(int pos) {
        if (pos >= listTicket.size()) {
            return null;
        }

        return listTicket.get(pos);
    }

    public Ticket getTicket(String placa) {
        for (Ticket tick : listTicket) {
            if (tick.getPlaca().equals(placa)) {
                return tick;
            }
        }

        return null;
    }
}
