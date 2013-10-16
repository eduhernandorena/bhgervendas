package br.com.dao;

import br.com.bean.TabelaPreco;
import br.com.bean.Ticket;
import br.com.bean.enumeration.StatusTicket;
import br.com.util.DBConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TicketDAO {

    private Connection conn = DBConnection.getConnection();

    public Boolean create(Ticket tk) {
        try {
            String sql = "INSERT INTO Ticket(id, dataent, horaent, status, idtabela, placa, serie)"
                    + " VALUES(NEXTVAL('seqticket'),?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            try {
                pst.setDate(1, new Date(tk.getDataEnt().getTime()));
                pst.setTimestamp(2, new Timestamp(tk.getHoraEnt().getTime()));
                pst.setInt(3, tk.getStatus().ordinal());
                pst.setLong(4, tk.getTabela().getId());
                pst.setString(5, tk.getPlaca());
                pst.setString(6, tk.getSerie());
                pst.execute();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public Boolean upDate(Ticket tk) {
        try {
            String sql = "UPDATE Ticket SET dataent=?, horaent=?, datasai=?, horasai=?, "
                    + "status=?, tempo=?, idtabela=?, placa=?, serie=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setDate(1, new Date(tk.getDataEnt().getTime()));
                pst.setTimestamp(2, new Timestamp(tk.getHoraEnt().getTime()));
                pst.setDate(3, new Date(tk.getDataSai().getTime()));
                pst.setTimestamp(4, new Timestamp(tk.getHoraSai().getTime()));
                pst.setInt(5, tk.getStatus().ordinal());
                pst.setString(6, "12:20");
                pst.setLong(7, tk.getTabela().getId());
                pst.setString(8, tk.getPlaca());
                pst.setString(9, tk.getSerie());
                pst.setLong(10, tk.getId());
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public List<Ticket> findAll() {
        try {
            PreparedStatement query = conn.prepareStatement("select * from ticket where status=0");
            ResultSet rs = query.executeQuery();
            return fillTicket(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Ticket> fillTicket(ResultSet rs) throws SQLException {
        List<Ticket> list = null;
        if (rs != null) {
            list = new ArrayList<>();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setSerie(rs.getString("serie"));
                ticket.setPlaca(rs.getString("placa"));
                ticket.setTempo(rs.getString("tempo"));
                ticket.setDataEnt(rs.getDate("dataent"));
                ticket.setDataSai(rs.getDate("datasai"));
                ticket.setHoraEnt(rs.getTimestamp("horaent"));
                ticket.setHoraSai(rs.getTimestamp("horasai"));
                ticket.setStatus(StatusTicket.value(rs.getInt("status")));
                ticket.setTabela(new TabelaPrecoDAO().find(rs.getLong("idtabela")));

                list.add(ticket);
            }
        }
        return list;
    }
}
