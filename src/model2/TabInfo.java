/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.sql.Connection;

/**
 *
 * @author norhan
 */
public class TabInfo {
    String Name;
    String Sensors;
    String SubSystem;
    String Frame;
    String Session;
    String SensorValue;
    String Time;
    String GrondTme;
    String ObcTime;
    String Mode;

    public TabInfo(String Name, String Sensors) {
        this.Name = Name;
        this.Sensors = Sensors;
    }

    public TabInfo() {
    }

    public String getName() {
        return Name;
    }

    public String getSensors() {
        return Sensors;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setSensors(String Sensors) {
        this.Sensors = Sensors;
    }

    public String getSubSystem() {
        return SubSystem;
    }

    public void setSubSystem(String SubSystem) {
        this.SubSystem = SubSystem;
    }

    public String getFrame() {
        return Frame;
    }

    public void setFrame(String Frame) {
        this.Frame = Frame;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String Session) {
        this.Session = Session;
    }

    public String getSensorValue() {
        return SensorValue;
    }

    public void setSensorValue(String SensorValue) {
        this.SensorValue = SensorValue;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getGrondTme() {
        return GrondTme;
    }

    public void setGrondTme(String GrondTme) {
        this.GrondTme = GrondTme;
    }

    public String getObcTime() {
        return ObcTime;
    }

    public void setObcTime(String ObcTime) {
        this.ObcTime = ObcTime;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String Mode) {
        this.Mode = Mode;
    }
    
    
}
