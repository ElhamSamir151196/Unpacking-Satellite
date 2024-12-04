/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author norhan
 */
public class SensorInfo {
         private String SubsystemName;
         private String APID;
         private String Name ;
         private String Unit ;
         private String Format;
         private String description ;
         private String Byte ;
         private String Bit ;
         private String Order ;
         private String MaxRange;
         private String MinRange;
         private String ValueON;
         private String ValueOFF;
         private int TypeID;
         private Boolean HasRange;

   
        // private Map<String,String> Status = new HashMap <String,String> ();
         private ArrayList<String> StatusNum = new ArrayList <String> ();
         private ArrayList<String> StatusValue = new ArrayList <String> ();
         private String StatusNum2="";
         private String StatusValue2="";
         private String NormalEquation ;
        // private Map<String,String> EquationStatus = new HashMap <String,String> ();
         private ArrayList<String> EquationStatusNum = new ArrayList <String> ();
         private ArrayList<String> EquationStatusValue = new ArrayList <String> ();
         private String EquationStatusNum2="";
         private String EquationStatusValue2="";
         

    public SensorInfo(String SubsystemName, String APID, String Name, String Unit, String Format, String description, String Byte, String Bit, String Order, String MaxRange, String MinRange, String ValueON, String ValueOFF, String NormalEquation) {
        this.SubsystemName = SubsystemName;
        this.APID = APID;
        this.Name = Name;
        this.Unit = Unit;
        this.Format = Format;
        this.description = description;
        this.Byte = Byte;
        this.Bit = Bit;
        this.Order = Order;
        this.MaxRange = MaxRange;
        this.MinRange = MinRange;
        this.ValueON = ValueON;
        this.ValueOFF = ValueOFF;
        this.NormalEquation = NormalEquation;
        StatusNum=null;
        StatusValue=null;
        EquationStatusNum=null;
        EquationStatusValue=null;
        
    }

    public SensorInfo() {
       
    }

    public void setSubsystemName(String SubsystemName) {
        this.SubsystemName = SubsystemName;
    }

    public void setAPID(String APID) {
        this.APID = APID;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSubsystemName() {
        return SubsystemName;
    }

    public String getAPID() {
        return APID;
    }

    public String getName() {
        return Name;
    }

    public String getUnit() {
        return Unit;
    }

    public String getFormat() {
        return Format;
    }

    public String getDescription() {
        return description;
    }

    public String getByte() {
        return Byte;
    }

    public String getBit() {
        return Bit;
    }

    public String getOrder() {
        return Order;
    }

    public String getMaxRange() {
        return MaxRange;
    }

    public String getMinRange() {
        return MinRange;
    }

    public String getValueON() {
        return ValueON;
    }

    public String getValueOFF() {
        return ValueOFF;
    }


    public String getNormalEquation() {
        return NormalEquation;
    }

  

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public void setFormat(String Format) {
        this.Format = Format;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setByte(String Byte) {
        this.Byte = Byte;
    }

    public void setBit(String Bit) {
        this.Bit = Bit;
    }

    public void setOrder(String Order) {
        this.Order = Order;
    }

    public void setMaxRange(String MaxRange) {
        this.MaxRange = MaxRange;
    }

    public void setMinRange(String MinRange) {
        this.MinRange = MinRange;
    }

    public void setValueON(String ValueON) {
        this.ValueON = ValueON;
    }

    public void setValueOFF(String ValueOFF) {
        this.ValueOFF = ValueOFF;
    }

    public ArrayList<String> getStatusNum() {
        return StatusNum;
    }

    public ArrayList<String> getStatusValue() {
        return StatusValue;
    }

    public ArrayList<String> getEquationStatusNum() {
        return EquationStatusNum;
    }

    public ArrayList<String> getEquationStatusValue() {
        return EquationStatusValue;
    }
    

    public void setStatusNum(ArrayList<String> StatusNum) {
        this.StatusNum = StatusNum;
        if(StatusNum!=null){
        for(int i=0;i<StatusNum.size();i++){
            StatusNum2+=StatusNum.get(i);
            StatusNum2+='\n';
        }
        }
    }

    public String getEquationStatusNum2() {
        return EquationStatusNum2;
    }

    public String getEquationStatusValue2() {
        return EquationStatusValue2;
    }

    public String getStatusNum2() {
        return StatusNum2;
    }

    public String getStatusValue2() {
        return StatusValue2;
    }
    
    public void setStatusValue(ArrayList<String> StatusValue) {
        this.StatusValue = StatusValue;
        if(StatusValue!=null){
        for(int i=0;i<StatusValue.size();i++){
            StatusValue2+=StatusValue.get(i);
            StatusValue2+='\n';
        }
        }
    }

    public void setEquationStatusNum(ArrayList<String> EquationStatusNum) {
        this.EquationStatusNum = EquationStatusNum;
        if(EquationStatusNum!=null){
          for(int i=0;i<EquationStatusNum.size();i++){
            EquationStatusNum2+=EquationStatusNum.get(i);
            EquationStatusNum2+='\n';
        }
        }
    }

    public void setEquationStatusValue(ArrayList<String> EquationStatusValue) {
        this.EquationStatusValue = EquationStatusValue;
        if(EquationStatusValue!=null){
         for(int i=0;i<EquationStatusValue.size();i++){
            EquationStatusValue2+=EquationStatusValue.get(i);
            EquationStatusValue2+='\n';
        }
        }
    }

 

    public void setNormalEquation(String NormalEquation) {
        this.NormalEquation = NormalEquation;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public int getTypeID() {
        return TypeID;
    }

    public Boolean getHasRange() {
        return HasRange;
    }

    public void setHasRange(Boolean HasRange) {
        this.HasRange = HasRange;
    }

         
}
