package br.com.dao;

import br.com.bean.Usuario;
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
public class UsuarioDAO {

    Connection conn = DBConnection.getConnection();

    public Usuario find(String user) {
        try {
            PreparedStatement query = conn.prepareStatement("select * from usuario o where o.user=?");
            query.setString(1, user);
            ResultSet rs = query.executeQuery();
            return fillUser(rs);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Usuario fillUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Usuario user = new Usuario();
            user.setId(rs.getLong("id"));
            user.setUser(rs.getString("user"));
            user.setPass(rs.getString("pass"));
        }
        return null;
    }

    public boolean validaUser(String user, String pass) {
        Usuario u = find(user);
        if (u.getPass().equals(pass)) {
            return true;
        } else {
            return false;
        }
    }
}
