/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author norhan
 */
public class Sensor {

    private final SimpleStringProperty Subsystem = new SimpleStringProperty();
    private final SimpleStringProperty Name = new SimpleStringProperty();//session number
    private  String FileName = "";
    private  String Date_Time = "";
    private  String Frame = "";
    private  String Sensor = "";
    private  String Value = "";
    private  String Mode = "";
    private  String GroundTime = "";
    private  String OBCTime = "";
    private  String Time = "";
    private int ID;

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getFrame() {
        return Frame;
    }

    public void setFrame(String Frame) {
        this.Frame = Frame;
    }

    public String getSensor() {
        return Sensor;
    }

    public void setSensor(String Sensor) {
        this.Sensor = Sensor;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }

    public String getGroundTime() {
        return GroundTime;
    }

    public void setGroundTime(String GroundTime) {
        this.GroundTime = GroundTime;
    }

    public String getOBCTime() {
        return OBCTime;
    }

    public void setOBCTime(String OBCTime) {
        this.OBCTime = OBCTime;
    }
    
    

   

    public void setName(String name) {
        Name.set(name);
    }
    
    public String getName() {
        return Name.get();
    }

    public void setSubsystem(String subsystem) {
        Subsystem.set(subsystem);
    }

    public String getSubsystem() {
        return Subsystem.get();
    }

    
    public String getFileName() {
        return FileName;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public void setDate_Time(String Date_Time) {
        this.Date_Time = Date_Time;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
