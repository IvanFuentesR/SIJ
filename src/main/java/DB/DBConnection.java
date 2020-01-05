package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Clase pensada para manejar las conexiones y querys a la base de datos
 */
/**
 *
 * @author cliente
 */
public class DBConnection {

    public Connection Conn;
    public PreparedStatement preparedStatement = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.Conn
                    = DriverManager.getConnection("jdbc:mysql://localhost/semaforo?"
                            + "user=root&password=4b0g4d0sf@M18");
            // Connection created correctly
            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean PrepareQuery(String sql) {
        try {
            this.preparedStatement = this.Conn.prepareStatement(sql);
            return true;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

    }

    public int ReleaseRSandSTMT() {

        //Se limpian los valores del result set y del statement
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
            } // ignore

            this.rs = null;
        }

        if (this.stmt != null) {
            try {
                this.stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore

            this.stmt = null;
        }

        return 0;

    }
}
