package br.com.dao;

import br.com.bean.TabelaPreco;
import br.com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Hernandorena
 */
public class TabelaPrecoDAO {

    Connection conn = DBConnection.getConnection();

    public TabelaPreco find() {
        try {
            PreparedStatement query = conn.prepareStatement("select * from tabelapreco");
            ResultSet rs = query.executeQuery();
            return fillTabelaPreco(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public TabelaPreco fillTabelaPreco(ResultSet rs) throws SQLException {
        TabelaPreco tab = null;
        if (rs.next()) {
            tab = new TabelaPreco();
            tab.setId(rs.getLong("id"));
            tab.setPlaca(rs.getString("placa"));
            tab.setTempo(rs.getString("tempo"));
            tab.setDataEnt(rs.getDate("dataent"));
            tab.setDataSai(rs.getDate("datasai"));
            tab.setHoraEnt(rs.getTimestamp("horaent"));
            tab.setHoraSai(rs.getTimestamp("horasai"));
            tab.setStatus(TabelaPreco.value(rs.getInt("status")));
            tab.setTabela(rs.getString("pass"));
        }
        return tab;
    }
}
