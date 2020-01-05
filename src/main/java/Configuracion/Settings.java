package Configuracion;

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
    private int DEFAULT = 0;
    private String GREEN = "GREEN";
    private String YELLOW = "YELLOW";
    private Preferences Prefs = Preferences.userRoot().node(this.getClass().getName());

    public Settings(int GreenValue, int YellowValue, int RedValue) {
        this.GreenValue = GreenValue;
        this.YellowValue = YellowValue;
    }

    public Settings() {
        this.GreenValue = this.GetGreenValue();
        this.YellowValue = this.GetYellowValue();
    }

    private int GetGreenValue() {
        return this.Prefs.getInt(this.GREEN, this.DEFAULT);
    }

    private int GetYellowValue() {
        return this.Prefs.getInt(this.YELLOW, this.DEFAULT);
    }

    public void SetGreenValue(int value) {
        this.GreenValue = value;
    }

    public void SetYellowValue(int value) {
        this.YellowValue = value;
    }

    public int SaveAllSettings() {
        if (this.GreenValue < this.YellowValue) {
            this.SaveGreenValue();
            this.SaveYellowValue();
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
}
