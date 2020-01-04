
import java.sql.Connection;
import java.sql.DriverManager;
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

    private Connection Conn;
    private Statement stmt = null;
    private ResultSet rs = null;

    private int Connect() {

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
            Logger.getLogger(SemaforoForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    private int ReleaseRSandSTMT() {

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
