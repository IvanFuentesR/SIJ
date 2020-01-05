/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuarios;

import DB.DBConnection;
import Semaforo.SemaforoForm;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cliente
 */
public class Usuario {

    private String Nombre;
    private String Apellido;
    private String Usuario;
    private char [] Password;

    public Usuario() {
        this.Nombre = "";
        this.Apellido = "";
        this.Usuario = "";
        this.Password = null;
    }

    public String GetNombre() {
        return this.Nombre;
    }

    public String GetApellido() {
        return this.Apellido;
    }

    public String GetUsuario() {
        return this.Usuario;
    }

    public String GetPassword() {
        return this.GetPassword();
    }

    public void SetNombre(String value) {
        this.Nombre = value;
    }

    public void SetApellido(String value) {
        this.Apellido = value;
    }

    public void SetUsuario(String value) {
        this.Usuario = value;
    }

    public void SetPassword(char [] value) {
        this.Password = value;
    }

    public boolean ValidateData() {
        if (this.Nombre.isEmpty() || this.Nombre == null || this.Apellido.isEmpty() || this.Apellido == null || this.Usuario.isEmpty() || this.Usuario == null || this.Password == null) {
            return false;
        }
        return true;

    }

    private String HashPassword() {
        Argon2 argon2 = Argon2Factory.create();
        // Hash password
        try {
        String hash = argon2.hash(10, 65536, 1, this.Password);
        // Verificamos la password
        if (argon2.verify(hash, this.Password)) {
            // Hash matches password
            return hash;
        } else {
            // Hash no hace match password
            return null;
        }
        } finally {
            argon2.wipeArray(this.Password);
        }

    }
    
    private boolean ComparePasswords(String hash) {
        Argon2 argon2 = Argon2Factory.create();
        if(argon2.verify(hash, this.Password)) {
            return true;
        } 
        return false;
        
    }
    
    private boolean UserExists() {
        DBConnection conn = new DBConnection();
         int id = 0;
          try {
                String sql = "SELECT id FROM `usuarios` WHERE usuario = ?;";
                conn.PrepareQuery(sql);
                conn.preparedStatement.setString(1, this.Usuario);
                conn.rs = conn.preparedStatement.executeQuery();
                conn.rs.next();
                id = conn.rs.getInt("id");
                if(id != 0) {
                    return true;
                }
          } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
          return false;
    }

    public boolean Register() {
        String HashedPassword = this.HashPassword();
        if (this.ValidateData() && HashedPassword != null && !this.UserExists()) {
            DBConnection conn = new DBConnection();
            try {
                String sql = "INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `usuario`, `password`, `created_at`, `updated_at`) VALUES (NULL, ?, ?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
                conn.PrepareQuery(sql);
                conn.preparedStatement.setString(1, this.Nombre);
                conn.preparedStatement.setString(2, this.Apellido);
                conn.preparedStatement.setString(3, this.Usuario);
                conn.preparedStatement.setString(4, HashedPassword);
                conn.preparedStatement.executeUpdate();
                return true;
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        }
        return false;
    }
    
    public boolean Login() {
        DBConnection conn = new DBConnection();
         try {
                int id = 0;
                String sql = "SELECT id FROM `usuarios` WHERE usuario = ?;";
                conn.PrepareQuery(sql);
                conn.preparedStatement.setString(1, this.Usuario);
                conn.rs = conn.preparedStatement.executeQuery();
                conn.rs.next();
                id = conn.rs.getInt("id");
                if(id != 0) {
                    System.out.println(id);
                    sql = "SELECT password FROM `usuarios` WHERE usuario = ?;";
                    conn.PrepareQuery(sql);
                    conn.preparedStatement.setString(1, this.Usuario);
                    conn.rs = conn.preparedStatement.executeQuery();
                    conn.rs.next();
                    String PasswordDB = conn.rs.getString("password");
                    if(this.ComparePasswords(PasswordDB)) {
                        return true;
                    }
                } 
                
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
        return false;
    }

}
