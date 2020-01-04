
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cliente
 */
public class Departamentos {

    public Departamentos() {
    }

    public int GetDepartamentoID(String Departamento) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn
                    = DriverManager.getConnection("jdbc:mysql://localhost/semaforo?"
                            + "user=root&password=4b0g4d0sf@M18");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                String sql = "SELECT id FROM departamentos WHERE nombre = ?;";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, Departamento);
                rs = preparedStatement.executeQuery();
                rs.next();
                int id = rs.getInt("id");
                return id;

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SemaforoForm.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 0;
    }

    public String[][] GetDepartamentos() {
        // TODO add your handling code here:
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn
                    = DriverManager.getConnection("jdbc:mysql://localhost/semaforo?"
                            + "user=root&password=4b0g4d0sf@M18");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                // Obtenemos el numero de Rows que va a regresar la consulta
                rs = stmt.executeQuery("SELECT count(*) FROM departamentos");
                rs.next();
                int rowsNumber = rs.getInt("count(*)");

                //Obtenemos la informacion y el numero de columnas
                rs = stmt.executeQuery("SELECT * FROM departamentos");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                //Creamos un arreglo basado en el numero de rows + columns
                String[][] Departamentos = new String[rowsNumber][columnsNumber];
                int c_rows = 0;
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) {
                            System.out.print(",  ");
                        }
                        Departamentos[c_rows][i - 1] = rs.getString(i);
                    }
                    c_rows++;
                }

                return Departamentos;

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SemaforoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int[] GetAccidentesAndIncidentes(int DepartamentoID) {
        // TODO add your handling code here:
        int[] Data = new int[3];
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn
                    = DriverManager.getConnection("jdbc:mysql://localhost/semaforo?"
                            + "user=root&password=4b0g4d0sf@M18");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();

                //Obtenemos los accidentes del departamento
                String sql = "SELECT count(*) FROM accidentes where departamento_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, DepartamentoID);
                rs = preparedStatement.executeQuery();
                rs.next();
                int Accidentes = rs.getInt("count(*)");

                //Obtenemos los incidentes del departamento
                sql = "SELECT count(*) FROM incidentes where departamento_id = ?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, DepartamentoID);
                rs = preparedStatement.executeQuery();
                rs.next();
                int Incidentes = rs.getInt("count(*)");
                
                //Obtenemos los dias sin accidentes del departamento
                sql = "SELECT datediff(created_at, CURRENT_TIMESTAMP) FROM accidentes where departamento_id = ? order by id DESC LIMIT 1";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, DepartamentoID);
                rs = preparedStatement.executeQuery();
                rs.next();
                int DiasSinAccidentes = rs.getInt("datediff(created_at, CURRENT_TIMESTAMP)");

                Data[0] = Accidentes;
                Data[1] = Incidentes;
                Data[2] = DiasSinAccidentes;
                
                return Data;
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return Data;
            } finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) {
                    } // ignore

                    stmt = null;
                }
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SemaforoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Data;
    }

}
