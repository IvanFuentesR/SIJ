
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

    public int GreenValue;
    public int YellowValue;
    public int RedValue;
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

    public void SetGreenValue(int value) {
        this.GreenValue = value;
    }

    public void SetYellowValue(int value) {
        this.YellowValue = value;
    }

    public void SetRedValue(int value) {
        this.RedValue = value;
    }

    public int SaveAllSettings() {
        if (this.GreenValue < this.YellowValue && this.YellowValue < this.RedValue) {
            this.SaveGreenValue();
            this.SaveYellowValue();
            this.SaveRedValue();
            return 0;
        }
        return 1;
    }

    private void SaveGreenValue() {
        this.Prefs.putInt(this.GREEN, this.GreenValue);
    }

    private void SaveYellowValue() {
        this.Prefs.putInt(this.YELLOW, this.YellowValue);
    }

    private void SaveRedValue() {
        this.Prefs.putInt(this.RED, this.RedValue);
    }

}
