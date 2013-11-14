package br.com.dao;

import br.com.bean.Ticket;
import br.com.rel.Relatorio;
import br.com.rel.decorator.RelDecorator;
import br.com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class RelatorioDAO {

    private Connection conn = DBConnection.getConnection();

    public String getAnalitico(Date dtInit, Date dtEnd) {
        String sql = "select * from ticket where dataent>=? and datasai<=? "
                + " and status=1";
        try {
            PreparedStatement query = conn.prepareStatement(sql);
            query.setDate(1, new java.sql.Date(dtInit.getTime()));
            query.setDate(2, new java.sql.Date(dtEnd.getTime()));
            ResultSet rs = query.executeQuery();
            List<Ticket> list = TicketDAO.fillTicket(rs);
            return new Relatorio().relAnalitico(list, dtInit, dtEnd);
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String getSintetico(Date dtInit, Date dtEnd) {
        String sql = "select count(*), sum(valor) from ticket where dataent>=? and datasai<=? "
                + " and status=1";
        try {
            PreparedStatement query = conn.prepareStatement(sql);
            query.setDate(1, new java.sql.Date(dtInit.getTime()));
            query.setDate(2, new java.sql.Date(dtEnd.getTime()));
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                RelDecorator dec = new RelDecorator();
                dec.setTotalVeic(rs.getInt(1));
                dec.setTotalPago(rs.getDouble(2));
                if (dec.getTotalPago() == 0.0 || dec.getTotalVeic() == 0.0) {
                    dec.setValorMedioPorVeic(0.0);
                } else {
                    dec.setValorMedioPorVeic(dec.getTotalPago() / dec.getTotalVeic());
                }
                return new Relatorio().relSintetico(dec, dtInit, dtEnd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
