package br.com.rel;

import br.com.bean.Ticket;
import br.com.dao.TicketDAO;
import br.com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class Relatorio {

    private Connection conn = DBConnection.getConnection();

    public List<Ticket> getAnalitico(Date dtInit, Date dtEnd) {
        String sql = "select * from ticket where dataent>=? and datasai<=? "
                + " and status=1";
        //and horaent>=? and horasai<=? 
        try {
            PreparedStatement query = conn.prepareStatement(sql);
            query.setDate(1, new java.sql.Date(dtInit.getTime()));
            query.setDate(2, new java.sql.Date(dtEnd.getTime()));
//            query.setDate(3, new java.sql.Date(horaInit.getTime()));;
//            query.setDate(4, new java.sql.Date(horaEnd.getTime()));
            ResultSet rs = query.executeQuery();
            List<Ticket> list = TicketDAO.fillTicket(rs);
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public String getSintetico(Date dtInit, Date dtEnd) {
        String sql = "select count(*), sum(valor) from ticket where dataent>=? and datasai<=? "
                + " and status=1";
        //and horaent>=? and horasai<=? 
        try {
            PreparedStatement query = conn.prepareStatement(sql);
            query.setDate(1, new java.sql.Date(dtInit.getTime()));
            query.setDate(2, new java.sql.Date(dtEnd.getTime()));
//            query.setDate(3, new java.sql.Date(horaInit.getTime()));;
//            query.setDate(4, new java.sql.Date(horaEnd.getTime()));
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getDouble(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
