/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author norhan
 */
public class Subsystem {
    private String Name;
    private int NumberOfSensors;
    private String [] Sensors;
    private unpacking unpackingObject;
    private DB db = new DB();
    private Map<String, List<String>> Limits;

    public int getNumberOfSensors() {
        return NumberOfSensors;
    }

    public unpacking getUnpackingObject() {
        return unpackingObject;
    }

    public DB getDb() {
        return db;
    }

    public void setNumberOfSensors(int NumberOfSensors) {
        this.NumberOfSensors = NumberOfSensors;
    }

    public void setSensors(String[] Sensors) {
        this.Sensors = Sensors;
    }

    public void setUnpackingObject(unpacking unpackingObject) {
        this.unpackingObject = unpackingObject;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public int getNumberOfSensors(String SystemName ) throws SQLException {
       return db.NumOfSensorsForSubSystem(SystemName,db.connectDB());
         
    }

    public String[] getSensors() {
        return Sensors;
    }

    public String[] setSensors(String SystemName) throws SQLException {
       String result = db.PACKETSENSORS(SystemName,db.connectDB());
     
      //System.out.println("test "+NumberOFPowersensors);
        if(result.equals(null)){
            return null;
        }
       // System.out.println("hi = "+result+"*****************************");
      String [] Sensors =result.split(",");
      return Sensors;
    }
    
   public ResultSet ReadData(int SessionID , String SystemName) throws SQLException{
        ResultSet result = db.SelectReadData(SessionID,SystemName,db.connectDB());
       // System.out.println(result.getArray(counter));
        
          //result.next();
          return result;
   }
}
