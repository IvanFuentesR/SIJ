
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cliente
 */
public class Settings {

    private int GreenValue;
    private int YellowValue;
    private int RedValue;
    private int DEFAULT = 0;
    private String GREEN = "GREEN";
    private String YELLOW = "YELLOW";
    private String RED = "RED";
    private Preferences Prefs = Preferences.userRoot().node(this.getClass().getName());
   

    public Settings(int GreenValue, int YellowValue, int RedValue) {
        this.GreenValue = GreenValue;
        this.YellowValue = YellowValue;
        this.RedValue = RedValue;
    }

    public Settings() {
      this.GreenValue = this.GetGreenValue();
      this.YellowValue = this.GetYellowValue();
      this.RedValue = this.GetRedValue();
    }

    private int GetGreenValue() {
        return this.Prefs.getInt(this.GREEN, this.DEFAULT);
    }

    private int GetYellowValue() {
        return this.Prefs.getInt(this.YELLOW, this.DEFAULT);
    }

    private int GetRedValue() {
        return this.Prefs.getInt(this.RED, this.DEFAULT);
    }

    public void SetGreenValue() {
        this.Prefs.putInt(this.GREEN, this.GreenValue);
    }

    public void SetYellowValue() {
        this.Prefs.putInt(this.YELLOW, this.YellowValue);
    }

    public void SetRedValue() {
        this.Prefs.putInt(this.RED, this.RedValue);
    }

}
