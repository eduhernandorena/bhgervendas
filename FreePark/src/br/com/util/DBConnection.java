package br.com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 * @author Roger
 */
public class DBConnection {

    /**
     * Método que retorna a conexao com o banco de dados.
     *
     * @param param um enum que define onde a conexão será realizada.
     * @return Retorna um ibjeto Connection com a conexão personalizada.
     */
    private static Connection conexao;

    public static Connection getConnection() {
        if (conexao != null) {
            return conexao;
        } else {
            return getRealConnection();
        }
    }

    /**
     * Método que recebe um nome de base e apartir deste retorna uma conexão com
     * o banco de dados via Sybase.
     *
     * @return Retorna uma conexão com a base de dados.
     */
    private static Connection getRealConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/FREEPARK";
            conexao = DriverManager.getConnection(url, "park", "parking");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexao;
    }
}
