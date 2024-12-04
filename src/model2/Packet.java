/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mostafa
 */
public class Packet {

    private String Data;
    private int PacketID;
    private String Sensors;
    private int PacketSequence;
    private String Time;
    
    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
    public int getPacketSequence() {
        return PacketSequence;
    }
    public void setPacketSequence(int PacketSequence) {
        this.PacketSequence = PacketSequence;
    }
    public String getSensors() {
        return Sensors;
    }
    public void setSensors(String Sensors) {
        this.Sensors = Sensors;
    }
    public int getPacketID() {
        return PacketID;
    }
    public void setPacketID(int PacketID) {
        this.PacketID = PacketID;
    }
    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
    public Packet(String Data,int PackrtID,String Sensors,int PacketSequence,String Time) {
        this.Data = Data;
        this.PacketID=PackrtID;
        this.Sensors=Sensors;
        this.PacketSequence=PacketSequence;
        this.Time=Time;
    }
    public Packet() {
        this.Data ="";
        this.PacketID=0;
        this.Sensors="";
        PacketSequence=0;
        Time="";
    }
    
    private String readfile(String fileName)
    {
        FileReader fileReader;
        String Data="";
        try {
            fileReader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fileReader);
            Data=in.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(unpacking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(unpacking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return " "+Data;
    }
    public unpacking SplitData(int Sequence, unpacking obj,String fileName,String Data) throws SQLException
    {
        System.out.println("****** split data fn.*******************");
        int Start_APID=obj.getStart_APID();
        int End_APID=obj.getEnd_APID();
        int Start_Time=obj.getStart_Time();
        int End_Time=obj.getEnd_Time();
        int Start_Data=obj.getStart_Data();
        int End_Data=obj.getEnd_Data();
        String type=obj.getType();
        //String Data=readfile( fileName);
        int startAPID=0,endAPID=0,startData=0,endData=0,startTime=0,endTime=0;
        int LengthAPID=0,LengthData=0,LengthTime=0;
        String data="";
        Packet temp;
         if (type.equals("bits"))
        {
            startAPID=Start_APID/8;
            endAPID=End_APID/8;
            startData=Start_Data/8;
            endData=End_Data/8;
            startTime=Start_Time/8;
            endTime=End_Time/8;
            int temp1=startAPID,temp3=startData,temp4=startTime;
            if (Start_APID%8==0){startAPID--;}
            if (Start_Data%8==0){startData--;}
            if (Start_Time%8==0){startTime--;}
            if (End_APID%8!=0){endAPID++;}
            if (End_Data%8!=0){endData++;}
            if (End_Time%8!=0){endTime++;}
            LengthAPID=(Start_APID-(temp1*8));
            LengthData=(Start_Data-(temp3*8));
            LengthTime=(Start_Time-(temp4*8));
        }
        else if (type.equals("bytes"))
        {
            startAPID=Start_APID-1;
            endAPID=End_APID;
            startData=Start_Data-1;
            endData=End_Data;
            startTime=Start_Time-1;
            endTime=End_Time;
        }

         
        while (Data.length()>0)
        {
             //Data=Data.substring(1,Data.length());
             System.out.println("Data before spaces = "+Data);
             Data=Data.replaceAll("..", "$0 ");//make space between every 2 char to devied to bytes.
             System.out.println("Data after spaces= "+Data);
             temp=new Packet();
             
             
             System.out.println("startAPID = "+startAPID+",endAPID = "+endAPID);
             String APID=Data.substring(((startAPID*2)+startAPID), ((endAPID*2)+endAPID-1));
             System.out.println("*********************APID = "+APID);
            String data1="";
            data1=obj.getConvert().HexToBinary(APID, obj.getConvert());
            System.out.println("data1 = "+data1);

            data1=data1.substring(3,data1.length());
            
            System.out.println("data1 = "+data1);
            int apid=obj.getConvert().ConvertBinaryToDecimal(data1);
            System.out.println("apid value = "+apid);
            System.out.println("startAPID = "+startAPID+",endAPID = "+endAPID);
             System.out.println("length = "+apid+"*************************");
             
             
             ////////// length data /////////////////////
             String DataLength=Data.substring(((startData*2)+startData), ((endData*2)+endData-1));
             System.out.println("*********************APID = "+DataLength);
             int dataLength=determine(DataLength,LengthData,obj)+1;
             
             System.out.println("length = "+dataLength+"*************************");
             System.out.println("obj.getEnd_Time() = "+obj.getEnd_Time() +" , obj.getStart_Time() ="+obj.getStart_Time());
             
             ////////// Tme///////////////
             if (obj.getEnd_Time()!=0 && obj.getStart_Time()!=0)
             {
                 temp.setTime(Data.substring(((startTime*2)+startTime), ((endTime*2)+endTime-1)));
             }
             
             int data_length=26,start_date=37;
             //////////////// telmetry Data//////////////////
            // data=Data.substring(((endData*2)+endData),(((dataLength*2)+(dataLength-1))+((endData*2)+endData)));

             data=Data.substring(((start_date*2)+start_date),(((data_length*2)+(data_length-1))+((start_date*2)+start_date)));
             //System.out.println("data = "+data);
             //System.out.println("from : "+(((data_length*2)+(data_length-1))+((start_date*2)+start_date))+" , to : "+Data.length());
             Data="";//Data.substring((((data_length*2)+(data_length-1))+((start_date*2)+start_date)),Data.length());
            // System.out.println("from : "+(((dataLength*2)+(dataLength-1))+((endData*2)+endData))+" , to : "+Data.length());
             //System.out.println("Data = "+Data);
             //System.out.println("obj.getTablePackets().containsKey(apid) = " + obj.getTablePackets().containsKey(apid));
             if(obj.getTablePackets().containsKey(apid))//apiad is 816 decimal of packet address 
             {
               //  System.out.println("*************** inside condition ***************");
                 Sequence++;
                 System.out.println("sequesnce = "+Sequence);
                 List<String> val = new ArrayList<String>();
                 val=obj.getTablePackets().get(apid);
                 temp.setPacketID(Integer.parseInt(val.get(0)));
                 //System.out.println("val.get(0)  = "+val.get(0));
                 temp.setSensors(val.get(1));
                 //System.out.println("val.get(1)  = "+val.get(1));
                 temp.setData(data);
                 //System.out.println("data = "+data);
                 temp.setPacketSequence(Sequence);
                 //System.out.println("sequesnce = "+Sequence);
                 obj.getPackets().add(temp);
                 //System.out.println("Table packet = "+Integer.parseInt(val.get(0))+" , sensor = "+val.get(1)+" , Sequence = "+Sequence);
             }
        }
        
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
      /*  String Data=readfile( fileName);
       String  data=Data.substring(56,Data.length());
       
*/
      
      /* System.out.println("****** split data fn.*******************");
        int Start_APID=obj.getStart_APID();
        int End_APID=obj.getEnd_APID();
        int Start_Time=obj.getStart_Time();
        int End_Time=obj.getEnd_Time();
        int Start_Data=obj.getStart_Data();
        int End_Data=obj.getEnd_Data();
        String type=obj.getType();
        String Data=readfile( fileName);
        int startAPID=0,endAPID=0,startData=0,endData=0,startTime=0,endTime=0;
        int LengthAPID=0,LengthData=0,LengthTime=0;
        String data="";
        Packet temp;
        if (type.equals("bits"))
        {
            startAPID=Start_APID/8;
            endAPID=End_APID/8;
            startData=Start_Data/8;
            endData=End_Data/8;
            startTime=Start_Time/8;
            endTime=End_Time/8;
            int temp1=startAPID,temp3=startData,temp4=startTime;
            if (Start_APID%8==0){startAPID--;}
            if (Start_Data%8==0){startData--;}
            if (Start_Time%8==0){startTime--;}
            if (End_APID%8!=0){endAPID++;}
            if (End_Data%8!=0){endData++;}
            if (End_Time%8!=0){endTime++;}
            LengthAPID=(Start_APID-(temp1*8));
            LengthData=(Start_Data-(temp3*8));
            LengthTime=(Start_Time-(temp4*8));
        }
        else if (type.equals("bytes"))
        {
            startAPID=Start_APID-1;
            endAPID=End_APID;
            startData=Start_Data-1;
            endData=End_Data;
            startTime=Start_Time-1;
            endTime=End_Time;
        }
        while (Data.length()>0)
        {
             Data=Data.substring(1,Data.length());
             System.out.println("Data = "+Data);
             temp=new Packet();
             System.out.println("startAPID = "+startAPID+",endAPID = "+endAPID);
             String APID=Data.substring(((startAPID*2)+startAPID), ((endAPID*2)+endAPID-1));
             System.out.println("*********************APID = "+APID);
             int apid=determine(APID,LengthAPID,obj);
             
             System.out.println("startAPID = "+startAPID+",endAPID = "+endAPID);
             System.out.println("length = "+apid+"*************************");
             //ResultSet Table=obj.getDb().SelectPacketID(apid, obj.getDb().getConnection());
             String DataLength=Data.substring(((startData*2)+startData), ((endData*2)+endData-1));
             System.out.println("*********************APID = "+DataLength);

             int dataLength=determine(DataLength,LengthData,obj)+1;
             System.out.println("length = "+dataLength+"*************************");
             System.out.println("obj.getEnd_Time() = "+obj.getEnd_Time() +" , obj.getStart_Time() ="+obj.getStart_Time());
             if (obj.getEnd_Time()!=0 && obj.getStart_Time()!=0)
             {
                 temp.setTime(Data.substring(((startTime*2)+startTime), ((endTime*2)+endTime-1)));
             }
             data=Data.substring(((endData*2)+endData),(((dataLength*2)+(dataLength-1))+((endData*2)+endData)));
             System.out.println("data = "+data);
             System.out.println("from : "+(((dataLength*2)+(dataLength-1))+((endData*2)+endData))+" , to : "+Data.length());
             Data=Data.substring((((dataLength*2)+(dataLength-1))+((endData*2)+endData)),Data.length());
            // System.out.println("from : "+(((dataLength*2)+(dataLength-1))+((endData*2)+endData))+" , to : "+Data.length());
             System.out.println("Data = "+Data);
             System.out.println("obj.getTablePackets().containsKey(apid) = " + obj.getTablePackets().containsKey(apid));
             if(obj.getTablePackets().containsKey(apid))//apiad is 816 decimal of packet address 
             {
                 System.err.println("*************** inside condition ***************");
                 Sequence++;
                 List<String> val = new ArrayList<String>();
                 val=obj.getTablePackets().get(apid);
                 temp.setPacketID(Integer.parseInt(val.get(0)));
                 System.out.println("val.get(0)  = "+val.get(0));
                 temp.setSensors(val.get(1));
                 System.out.println("val.get(1)  = "+val.get(1));
                 temp.setData(data);
                 System.out.println("data = "+data);
                 temp.setPacketSequence(Sequence);
                 System.out.println("sequesnce = "+Sequence);
                 obj.getPackets().add(temp);
                 System.out.println("Table packet = "+Integer.parseInt(val.get(0))+" , sensor = "+val.get(1)+" , Sequence = "+Sequence);
             }
        }*/
        return obj;
    }
    public int determine(String Data,int length,unpacking obj)
    {
        String arr[]=Data.split(" ");
        System.out.println("****** Data = "+Data);
        String data="";
        for (int i=0;i<arr.length;i++)
        {
            data+=obj.getConvert().HexToBinary(arr[i], obj.getConvert());
            System.out.println("data = "+data);
        }
        if (length!=0)
        {
            data=data.substring(length,data.length());
        }
        System.out.println("========== data = "+data);
        int value=obj.getConvert().ConvertBinaryToDecimal(data);
        System.out.println("value = "+value);
        return value;
    }
    public String Timecalculation(Packet obj,Conversions convert){
        String declength="";
        String hexatime="";
        String hexadata=obj.getData();
        String bytes[]=hexadata.split(" ");
        for(int i=0;i<bytes.length;i++){
            declength+=convert.HexToBinary(""+bytes[i].charAt(0),convert);
            declength+=convert.HexToBinary(""+bytes[i].charAt(1),convert);
        }
        int length = convert.ConvertBinaryToDecimal(declength);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 1958);
        cal.add(Calendar.SECOND, length);
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String s = formatter.format(cal.getTime());
        return s;
    }
}
