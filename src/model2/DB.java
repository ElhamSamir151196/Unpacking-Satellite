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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Mostafa
 */
public class DB {

    private Connection connection;

    public DB() {
        this.connection = null;
    }

    public DB(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection connectDB() {
        Connection connection = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            //String dbURL="jdbc:mysql://localhost:3306/cubesat";//cubesat_copy
            String dbURL = "jdbc:mysql://localhost:3306/cubesat_copy";
            String Username = "root";
            String password = "root123";
            String file = "";
            try {
                file = readFromfile("fileDB.txt");//get path from file.
                String[] array=file.split(",");
                Username = array[0];
                password = array[1];
                System.out.println("Username = "+Username+" , password = "+password);
                System.out.println("Read fine = " + file);
            } catch (FileNotFoundException e) {
                System.out.println("errorrrrrrrrrrrrrrr");
            } catch (IOException ex) {
                System.out.println("problemmmmmmmmmm");
                //Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection(dbURL, Username, password);

            System.out.println("Success");
        } catch (Exception e) {
            Notifications notification = Notifications.create()
                    .title("DataBase Connection")
                    .text("DataBase Connection Error! ")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
            System.out.println("connection failure");
        }
        return connection;
    }

    public static String readFromfile(String fileName) throws IOException {
        FileReader fileReader
                = new FileReader(fileName);
        String line, result = "";

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader
                = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Always close files.
        bufferedReader.close();
        return result;
    }

    void InsertSession(int SessionID, int SessionNo, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `session`(`SESSIONID`, `SESSIONNO`) VALUES (" + SessionID + "," + SessionNo + ")";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
            System.out.println("flag = " + flag);
        } catch (Exception e) {
            System.out.println("Error in Session statement");
        }
    }

    void InsertPacketRecieve(String PacketAddress, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `packetrecive`(`PACKETSEQUENCE`, `SESSIONID`, `PACKETID`, `TIME` ,`File_Name`) VALUES" + PacketAddress;
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in packetrecive statement");
        }
    }

    void InsertData(String Data, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `storage`(`sessionID`, `PACKETSEQUENCE`, `SENSORNO`, `CODE`, `VALUE`, `Time`, `Ground_Time`, `OBC_Time`, `Mode`, `Subsystem`, `Frame`) VALUES " + Data;
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in InsertReads statement");
        }
    }

    void InsertSelectedSession(String SelectedID, String SessionSelected, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `sessionselected`(`SelectedID`, `SessionSelected`) VALUES ('" + SelectedID + "','" + SessionSelected + "')";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in InsertReads statement");
        }
    }

    void InsertSystem(String SubSystemID, String Description, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `system`(`SYSTEMID`, `SYSTEMDESCRIPTION`) VALUES ('" + SubSystemID + "','" + Description + "')";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Insert SubSystem statement");
        }
    }

    void InsertStandard(int APID, int Data, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `standard`(`APID`, `DATA`) VALUES (" + APID + "," + Data + ")";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Insert SubSystem statement");
        }
    }

    void InsertPacket(int PacketID, int SystemID, int PacketAddress, String PacketSensors, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `packet`(`PACKETID`, `SYSTEMID`, `PACKETADDRESS`, `PACKETSENSORS`) VALUES (" + PacketID + "," + SystemID + "," + PacketAddress + ",'" + PacketSensors + "')";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Insert Packet Table statement");
        }
    }

    void InsertSenssorsAddress(int ByteNumber, int PacketID, int Sensorno, int Bits, int Order, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `address`(`BYTENO`, `PACKETID`, `SENSORNO`, `BITS`, `BYTEORDER`) VALUES (" + ByteNumber + "," + PacketID + "," + Sensorno + "," + Bits + "," + Order + ")";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Insert Address Table statement");
        }
    }

    void InsertSenssor(int SensorNo, int TypeID, String SensorUnit, String DisplayFormat, String Format, int max, int min, String SensorName, String SensorDescription, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `sensors`(`SENSORNO`, `TYPEID`, `SENSORUNIT`, `DSISPLAYFORMAT`, `FORMAT`, `MIN_VALUE`, `MAX_VALUE`, `SENSORNAME`, `SENSORDESC`) VALUES (" + SensorNo + "," + TypeID + ",'" + SensorUnit + "','" + DisplayFormat + "','" + Format + "'," + max + "," + min + ",'" + SensorName + "','" + SensorDescription + "')";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Insert Sensors Table statement");
        }
    }

    void InsertType(int TypeID, String Description, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `type`(`TYPEID`, `DESCRIPTION`) VALUES (" + TypeID + ",'" + Description + "')";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Type statement");
        }
    }

    void InsertTypes(int TypeID, String MessageDescOrEquation, String MessageCode, int MessageIndex, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `type1`(`TYPEID`, `MESSAGEDESC`, `MESSAGECODE`, `MessageIndex`) VALUES (" + TypeID + ",'" + MessageDescOrEquation + "','" + MessageCode + "'," + MessageIndex + ")";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in Types statement");
        }
    }

    void InsertTypeInterval(int x, int y, int MessageIndex, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `type3_1`(`X`, `Y`, `messageIndex`) VALUES (" + x + "," + y + "," + MessageIndex + ")";
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in TypeInterval statement");
        }
    }

    void Updata(String NameUpdatedVariable, String NewValue, String TableName, Connection connection) throws SQLException {
        try {
            String query = "UPDATE " + TableName + " SET " + NameUpdatedVariable + " = " + NewValue;
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Error in updata statement");
        }
    }

    void Delete(String NameDeletedVariable, String DeletedValue, String TableName, Connection connection) throws SQLException {
        String query = "DELETE FROM " + TableName + " WHERE " + NameDeletedVariable + " = " + DeletedValue;
        Statement statement = connection.createStatement();
        int flag = statement.executeUpdate(query);
    }

    void DeleteTable(String TableName, Connection connection) throws SQLException {
        String query = "DELETE FROM " + TableName;
        Statement statement = connection.createStatement();
        int flag = statement.executeUpdate(query);
    }

    ResultSet SelectAllTable(String TableName, Connection connection) throws SQLException {
        String query = "SELECT * FROM " + TableName;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectMaxSession(Connection connection) throws SQLException {
        String query = "SELECT `SESSIONNO`,MAX(`SESSIONID`) AS `largest` FROM SESSION WHERE `SESSIONID`=(SELECT MAX(`SESSIONID`) FROM SESSION)";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectMaxSessionSelection(Connection connection) throws SQLException {
        ResultSet Table = null;
        System.out.println("insidee get max*******");
        try {
            String query = "SELECT MAX(SelectedID) FROM sessionselected ";
            Statement statement = connection.createStatement();
            System.out.println("error here as i think");
            Table = statement.executeQuery(query);
            System.out.println("in try sucess*****");
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println(" in catch check why*****");
        }
        return Table;
    }

    ResultSet SelectMaxPacket(Connection connection) throws SQLException {
        String query = "SELECT `PACKETSEQUENCE`,MAX(`PACKETSEQUENCE`) AS `largest` FROM packetrecive WHERE `PACKETSEQUENCE`=(SELECT MAX(`PACKETSEQUENCE`) FROM packetrecive)";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectStandard(Connection connection) throws SQLException {
        String query = "SELECT * FROM `standard`";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectPacketID(int APID, Connection connection) throws SQLException {
        String query = "SELECT * FROM `packet` WHERE `PACKETADDRESS`=" + APID;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectAllPacket(Connection connection) throws SQLException {
        String query = "SELECT * FROM `packet`";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectSensor(String SensorName, Connection connection) throws SQLException {
        String query = "SELECT * FROM `sensors` WHERE `SENSORNAME` = '" + SensorName + "'";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectAllSensors(Connection connection) throws SQLException {
        String query = "SELECT * FROM `sensors`";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectType(int Type, int SensorID, Connection connection) throws SQLException {
        String query = "SELECT * FROM `type" + Type + "` WHERE `MessageIndex` = " + SensorID + "";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectAddress(int SensorID, Connection connection) throws SQLException {
        String query = "SELECT * FROM `address` WHERE `SENSORNO` = " + SensorID + " ORDER BY BYTEORDER ASC";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    ResultSet SelectReadData(int sessionID, String SubSystemName, Connection connection) throws SQLException { //`VALUE`
        /*String query="SELECT * FROM `storage`,`packet`,`system`\n" +
            "WHERE storage.sessionID="+sessionID+" AND packet.SYSTEMID=(select system.SYSTEMID as ID from system where system.SYSTEMDESCRIPTION='"+SubSystemName
                +"') AND packet.PACKETID=(select packet.PACKETID as ID from packet where packet.SYSTEMID=(select system.SYSTEMID as ID from system where system.SYSTEMDESCRIPTION='"+SubSystemName+"'))";
        
         */
        String query = "SELECT `VALUE` FROM `storage`" + "WHERE storage.sessionID=" + sessionID;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    int CountRows(String TableName, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) AS Count FROM " + TableName;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        Table.next();
        int Count = Integer.parseInt(Table.getString("Count"));
        return Count;
    }

    ResultSet Selectbysesnorreadbytimerange(String Systemname, String sensorname, String firsttime, String secondtime, Connection connection) throws SQLException {
        String tablename = Systemname + "SubSystem";
        String query = "SELECT VALUE,Time FROM `storage` a,`packetrecive` p, `sensors` s,`packet`,`system`WHERE s.SENSORNO=a.SENSORNO and s.PACKETSEQUENCE=p.PACKETSEQUENCE and \n"
                + "p.PACKETID=`packet`.`PACKETID` and `packet`.`SYSTEMID`=`system`.`SYSTEMID`\n"
                + " `storage`.SENSORNAME=" + sensorname + " and `storage`.`Time` between " + "\"" + firsttime + "\"" + "and" + "\"" + secondtime + "\"`system`.`SYSTEMDESCRIPTION`=" + tablename;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;// lsa el min w el max

    }
//    String PACKETSENSORS(String subsystemname,Connection connection) throws SQLException
//    {
//        
//        String query="SELECT `PACKETSENSORS` FROM `system` JOIN `packet` WHERE packet.SYSTEMID=system.SYSTEMID and system.SYSTEMDESCRIPTION='"
//                +subsystemname+" SubSystem'";
//        
//        Statement statement=connection.createStatement();
//        ResultSet Table=statement.executeQuery(query);
//        Table.next();
//        String PACKETSENSORS=Table.getString("PACKETSENSORS");
//        return PACKETSENSORS;
//    }

    ResultSet SelectAllsensorspositions(String Subsystemname, Connection connection) throws SQLException {
        String query = "SELECT BYTENO,SENSORNO,BITS,BYTEORDER FROM `address` a,`packet` p, `system` s where a.PACKETID=p.PACKETID and p.SYSTEMID=s.SYSTEMID and s.SYSTEMDESCRIPTION=" + Subsystemname + " SubSystem";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    int NumOfSensorsForSubSystem(String SubSystem, Connection connection) throws SQLException {
        String query = "SELECT PACKETSENSORS FROM system JOIN packet WHERE system.SYSTEMID=packet.SYSTEMID AND system.SYSTEMDESCRIPTION='" + SubSystem + "'";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        Table.next();
        String sensors[] = Table.getString("PACKETSENSORS").split(",");
        return sensors.length;
    }

    String PACKETSENSORS(String subsystemname, Connection connection) throws SQLException {
        String query = "SELECT PACKETSENSORS FROM packet x,system y WHERE x.SYSTEMID=y.SYSTEMID and y.SYSTEMDESCRIPTION='" + subsystemname + " SubSystem' ";

        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        if (Table.next()) {

            String PACKETSENSORS = Table.getString("PACKETSENSORS");
            return PACKETSENSORS;
        }
        return null;
    }

    String TabsName(String subsystemname, Connection connection) throws SQLException {
        String query = "SELECT tabname FROM usertab where Subsystem='" + subsystemname + "' ";

        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        String PACKETSENSORS = "";
        while (Table.next()) {

            PACKETSENSORS = PACKETSENSORS + Table.getString("tabname") + ",";

        }
        return PACKETSENSORS;
    }

    ResultSet SESSIONSELECTED(String subsystemname, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        /*    System.out.println("***** DB **************");
        String query1 = "SELECT SessionSelected FROM sessionselected WHERE SelectedID =(SELECT MAX(SelectedID) FROM sessionselected)";
//        String query1 = "SELECT MAX(SelectedID) FROM sessionselected";
        //"SELECT PACKETSENSORS FROM packet x,system y WHERE x.SYSTEMID=y.SYSTEMID and y.SYSTEMDESCRIPTION='" + subsystemname + " SubSystem' ";

        ResultSet Table1 = statement.executeQuery(query1);
        int index =0;
        while (Table1.next()) {

            System.out.println("index11 = "+index+"******");
            System.out.println("SessionSelected = "+Table1.getString(1));
            index++;
        }*/
        System.out.println("***** DB **************");

        String query = "SELECT * FROM storage WHERE sessionID in (SELECT SessionSelected FROM sessionselected WHERE SelectedID =(SELECT MAX(SelectedID) FROM sessionselected))";
        // Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    String PACKETSESSIONS(String subsystemname, Connection connection) throws SQLException {
        String query = "SELECT SESSIONID FROM packetrecive x,system y WHERE x.PACKETID=y.SYSTEMID and y.SYSTEMDESCRIPTION='" + subsystemname + " SubSystem' ";

        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);

        String PACKETSENSORS = "";
        while (Table.next()) {

            PACKETSENSORS = PACKETSENSORS + Table.getString("SESSIONID") + ",";

        }

        return PACKETSENSORS;
    }

    String PACKETFILENAME(String subsystemname, Connection connection) throws SQLException {
        String query = "SELECT File_Name FROM packetrecive x,system y WHERE x.PACKETID=y.SYSTEMID and y.SYSTEMDESCRIPTION='" + subsystemname + " SubSystem' ";

        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        String PACKETSENSORS = "";
        while (Table.next()) {

            PACKETSENSORS = PACKETSENSORS + Table.getString("File_Name") + ",";

        }

        return PACKETSENSORS;
    }

    String PACKETDATEANDTIME(String subsystemname, Connection connection) throws SQLException {
        String query = "SELECT Time FROM packetrecive x,system y WHERE x.PACKETID=y.SYSTEMID and y.SYSTEMDESCRIPTION='" + subsystemname + " SubSystem' ";

        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        String PACKETSENSORS = "";
        while (Table.next()) {

            PACKETSENSORS = PACKETSENSORS + Table.getString("Time") + ",";

        }

        return PACKETSENSORS;
    }

    void Insertusertab(String SubSystem, String sensors, String Frame, String Session, String SensorValue, String Time, String GrondTme, String ObcTime, String Mode, String tabname, Connection connection) throws SQLException {
        try {
            String query = "INSERT INTO `usertab`(`tabname`, `sensors` , `Subsystem` , `sensorFrame` , `SensorSession` , `SensorValue` , `GroundTime` , `OBC_Time` , `Mode` , `SensorTime` )"
                    + " VALUES ('" + tabname + "','" + sensors + "','" + SubSystem + "','" + Frame + "','" + Session + "','" + SensorValue + "','" + GrondTme + "','" + ObcTime + "','" + Mode + "','" + Time + "')";

            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
            Notifications notification = Notifications.create()
                    .title("Sensors Selected")
                    .text("Store Successfully")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
        } catch (Exception e) {
            Notifications notification = Notifications.create()
                    .title("Sensors Selected")
                    .text("Store Error!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
            System.out.println("Error in Usertab statement");

            System.out.println(e.getMessage());
        }
    }

    ResultSet ShowUserTabs(Connection connection) throws SQLException {

        String query = "SELECT * FROM `usertab`";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

    int NumOfSensors(String TableName, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + TableName;
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        Table.next();
        int num = Table.getInt(1);
        return num;
    }

    String Selectsensorsoftab(String tabname, Connection connection) throws SQLException {
        String query = "SELECT `sensors` FROM `usertab` WHERE `tabname` = '" + tabname + "'";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        Table.next();
        String sensors = Table.getString("sensors");
        return sensors;
    }

    ResultSet Selectreadsoftab(String sensorname, String sessionid, Connection connection) throws SQLException {

        String query = "SELECT  `CODE`  FROM `storage`, `sensors`\n"
                + "WHERE storage.SENSORNO = sensors.SENSORNO and\n"
                + "sessionID='" + sessionid + "' and sensors.SENSORNAME= '" + sensorname + "'";
        Statement statement = connection.createStatement();
        ResultSet Table = statement.executeQuery(query);
        return Table;
    }

}
