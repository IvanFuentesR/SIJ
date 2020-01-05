package Departamentos;


import DB.DBConnection;
import Semaforo.SemaforoForm;
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
            DBConnection DB = new DBConnection();

            try {
                DB.stmt = DB.Conn.createStatement();
                String sql = "SELECT id FROM departamentos WHERE nombre = ?;";
                DB.PrepareQuery(sql);
                DB.preparedStatement.setString(1, Departamento);
                DB.rs = DB.preparedStatement.executeQuery();
                DB.rs.next();
                int id = DB.rs.getInt("id");
                return id;

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                DB.ReleaseRSandSTMT();
            }

       
        return 0;
    }

    public String[][] GetDepartamentos() {
        // TODO add your handling code here:
        DBConnection DB = new DBConnection();

            try {
                DB.stmt = DB.Conn.createStatement();
                // Obtenemos el numero de Rows que va a regresar la consulta
                DB.rs = DB.stmt.executeQuery("SELECT count(*) FROM departamentos");
                DB.rs.next();
                int rowsNumber = DB.rs.getInt("count(*)");

                //Obtenemos la informacion y el numero de columnas
                DB.rs = DB.stmt.executeQuery("SELECT * FROM departamentos");
                ResultSetMetaData rsmd = DB.rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                //Creamos un arreglo basado en el numero de rows + columns
                String[][] Departamentos = new String[rowsNumber][columnsNumber];
                int c_rows = 0;
                while (DB.rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) {
                            System.out.print(",  ");
                        }
                        Departamentos[c_rows][i - 1] = DB.rs.getString(i);
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
                DB.ReleaseRSandSTMT();
            }
        return null;
    }

    public int[] GetAccidentesAndIncidentes(int DepartamentoID) {
        // TODO add your handling code here:
        int[] Data = new int[3];
        DBConnection DB = new DBConnection();
            try {
                DB.stmt = DB.Conn.createStatement();
                //Obtenemos los accidentes del departamento
                String sql = "SELECT count(*) FROM accidentes where departamento_id = ?";
                DB.PrepareQuery(sql);
                DB.preparedStatement.setInt(1, DepartamentoID);
                DB.rs = DB.preparedStatement.executeQuery();
                DB.rs.next();
                int Accidentes = DB.rs.getInt("count(*)");

                //Obtenemos los incidentes del departamento
                sql = "SELECT count(*) FROM incidentes where departamento_id = ?";
                DB.PrepareQuery(sql);
                DB.preparedStatement.setInt(1, DepartamentoID);
                DB.rs = DB.preparedStatement.executeQuery();
                DB.rs.next();
                int Incidentes = DB.rs.getInt("count(*)");
                
                //Obtenemos los dias sin accidentes del departamento
                sql = "SELECT datediff(created_at, CURRENT_TIMESTAMP) FROM accidentes where departamento_id = ? order by id DESC LIMIT 1";
                DB.PrepareQuery(sql);
                DB.preparedStatement.setInt(1, DepartamentoID);
                DB.rs = DB.preparedStatement.executeQuery();
                DB.rs.next();
                int DiasSinAccidentes = DB.rs.getInt("datediff(created_at, CURRENT_TIMESTAMP)");

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
                DB.ReleaseRSandSTMT();
            }
    }

}
