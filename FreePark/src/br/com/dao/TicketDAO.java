package br.com.dao;

import br.com.bean.Ticket;
import br.com.bean.enumeration.StatusTicket;
import br.com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TicketDAO {

    Connection conn = DBConnection.getConnection();

    public List<Ticket> findAll() {
        try {
            PreparedStatement query = conn.prepareStatement("select * from ticket");
            ResultSet rs = query.executeQuery();
            return fillTicket(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Ticket> fillTicket(ResultSet rs) throws SQLException {
        List<Ticket> list = null;
        if (rs != null) {
            list = new ArrayList<>();
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
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
