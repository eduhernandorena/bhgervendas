package br.com.dao;

import br.com.bean.TabelaPreco;
import br.com.bean.enumeration.Modalidade;
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

    public TabelaPreco find(int mod) {
        try {
            PreparedStatement query = conn.prepareStatement("select * from tabelapreco o where o.mod=?");
            query.setInt(1, mod);
            ResultSet rs = query.executeQuery();
            return fillTabelaPreco(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public TabelaPreco find(long id) {
        try {
            PreparedStatement query = conn.prepareStatement("select * from tabelapreco o where o.id=?");
            query.setLong(1, id);
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
            tab.setHalfHora(rs.getDouble("prhalfhora"));
            tab.setMod(Modalidade.value(rs.getInt("mod")));
            tab.setPrFracao(rs.getDouble("prfracao"));
            tab.setPrHora(rs.getDouble("prhora"));
        }
        return tab;
    }
}
