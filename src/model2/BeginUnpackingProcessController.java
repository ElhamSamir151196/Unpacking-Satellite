/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import com.mysql.jdbc.StringUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javax.script.ScriptException;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author norhan
 */
public class BeginUnpackingProcessController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public AnchorPane main;
    private int NumberOFPowersensors;
    private int NumberOFOBCsensors;
    private String PowerSensors[];
    private String OBCSensors[];
    public TilePane loadPane;
    public TilePane loadPane1;
    public TilePane loadPane2;
    public TilePane loadPane3;
    public TilePane loadPane4;
    public TilePane loadPane5;
    public int countPanes = 0;
    public TilePane OBCTile;
    public GridPane gride;
    private Label loadingPower[];
    private Label loadingOBC[];
    private Label loadingPower_frames[];
    private String Powerarray[];
    private ArrayList<String> PowerGroundTime;
    private ArrayList<String> PowerOBCTime;
    private ArrayList<String> PowerMode;
    private int Powercounter = 0;
    private int Displaycounter = -1;
    private int OBCcounter = 0;
    private int frame_sensor = 0;
    private int counter = 0;
    Subsystem subsystem = new Subsystem();
    ResultSet Powerresult;
    ResultSet Powerresult1;
    ResultSet obcresult;
    private int frame_number = 0;
    private int frame_index = 0;
    private Map<String, List<String>> Limits;
    private Map<String, List<String>> OBCLimits;
    unpacking obj;

    public void loadHome() throws IOException {
        // System.out.println("model2.InitializeWindowController.loadHome()");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //FXMLLoader  loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        //Vbox = (AnchorPane) loader.load();
        main.getChildren().clear();
        main.getChildren().addAll(pane);

    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            initializePowerTables();
            //initializeOBCTables();
            convert();
            if(PowerOBCTime.size()==0){
                loadHome();
            }
            ViewPower();
//            System.out.println("NNNN");
        } catch (SQLException ex) {
            Logger.getLogger(BeginUnpackingProcessController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            Logger.getLogger(BeginUnpackingProcessController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BeginUnpackingProcessController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void convert() throws SQLException, ScriptException, IOException {
        String file = "";
        file = readFromfile("offlinefile.txt");//get path from file.

        obj = new unpacking();
        Packet p = new Packet();
        obj.getDb().setConnection(obj.getDb().connectDB());
        obj.getConvert().setConvertTable(obj.getConvert().Initialization(obj.getConvert().getConvertTable()));//intialize table to convert from hexa to binary
        // System.out.println("power table inizalization length = "+obj.getConvert().getConvertTable().size());
        obj.Standard(obj);// to know time,aiap , data of start and end of packet.
        obj.Map(obj);// mapping sensors
        obj.MapPackets(obj);//mapping packets
        int count = obj.CreateSession(obj);// return number of this session.
        //System.out.println("****************** count = " + count + "************");

        InputStream is = null;
        File f = new File(file);// open.getSelectedFile();
        is = new BufferedInputStream(new FileInputStream(f));
        int bytesCounter = 0;
        int value;
        String myString = "";
        StringBuilder set = new StringBuilder();
        StringBuilder sbResult = new StringBuilder();
        while ((value = is.read()) != -1) {
            //convert to hex value with "X" formatter
            set.append(String.format("%02X ", value));
            //if 90 bytes are read, reset the counter,
            if (bytesCounter == 90) {
                sbResult.append(set).append("\n");
                bytesCounter = 0;

            } else {
                bytesCounter++;
            }
            myString = myString + set.charAt(0) + set.charAt(1);
            set.setLength(0);

        }
        if (bytesCounter != 0) {
            //add spaces more formatting purpose only
            for (; bytesCounter < 90; bytesCounter++) {
                //1 character 3 spaces
                set.append("   ");
            }
            sbResult.append(set).append("\n");

        }
        //System.out.println("string = "+myString);

        String[] arr = myString.split("0D0A");
        // System.out.println("length = " +arr.length + " , file lenth = "+myString.length());

        int i = 0,length = 0;
        String Frame_hexa = "";
        
        for (String s : arr) {
            i++;
            if (s.length() < 60) {

                continue;
            } else {
                String x = s.substring(56, 58);
                String st = hexToBin(x);
                st = String.format("%08d", Integer.parseInt(st));
                String mode_frame = st.substring(0, 3);
                int ModeValue = Integer.parseInt(mode_frame, 2);
                String SatMode = "Unknown Mode";
                String SubSystem = st.substring(st.length() - 5);
                int decimalValue = Integer.parseInt(SubSystem, 2);
                if (decimalValue == 9) {
                    //System.out.println("i = "+i+" --> "+s); 
                    length++;
                    // System.out.println("*********** frame "+length+"*****************");
                    frame_number = length;
                    p = new Packet();
                    System.out.println("s = "+s+"\n i = "+i);
                    p.SplitData(count, obj, file, s);// to inialize apiad and split date and header .
                    p = null;
                    if (ModeValue == 1) {
                        SatMode = "Initialization";
                    } else if (ModeValue == 2) {
                        SatMode = "Standby";
                    } else if (ModeValue == 3) {
                        SatMode = "CS";
                    } else if (ModeValue == 4) {
                        SatMode = "Imaging";
                    } else if (ModeValue == 5) {
                        SatMode = "Detumbling";
                    } else if (ModeValue == 6) {
                        SatMode = "Emergency";
                    } else {
                        SatMode = "Unknown Mode";
                    }
                    //System.out.println(" Mode = "+SatMode);
                    PowerMode.add(SatMode);
                    String OBC_Time = s.substring(29 * 2, 74);// end at 29*2+2*8
                    // System.out.println("OBC Time = "+OBC_Time);
                    String newTime = "";
                    for (int k = 0; k < 8; k++) {
                        newTime = OBC_Time.substring(k * 2, k * 2 + 2) + newTime;
                    }
                    //System.out.println("new time = "+newTime);
                    int num = Integer.parseInt(newTime, 16);
                    //System.out.println("num = "+num);
                    GregorianCalendar baseTime1 = new GregorianCalendar(2000, Calendar.JANUARY, 2, 02, 00, 00);
                    baseTime1.add(Calendar.SECOND, num);
                    //System.out.println("base time = "+baseTime1.getTime());
                    PowerOBCTime.add(baseTime1.getTime() + "");
                    String hex = s.substring(0, 46);
                    //          System.out.println(" hexa ground time = "+hex);
                    StringBuilder output = new StringBuilder();
                    for (int j = 0; j < hex.length(); j += 2) {
                        String str = hex.substring(j, j + 2);
                        output.append((char) Integer.parseInt(str, 16));
                    }
                    PowerGroundTime.add(output + "");
                }
            }
        }

       if(length==0 ){
            Notifications notification = Notifications.create()
                        .title("Unpacking")
                        .text("file has no power Frames")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.CENTER);
                notification.show();
                return;
       }
        obj.PacketInformation(obj, file,PowerGroundTime,PowerOBCTime,PowerMode);
        System.out.println("finisheddddddddddddddddddddddddddddddddddddd");
    }

    public String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }

    public void ViewPower() throws Exception {

        ReadPowerData();
        createLoadPane();
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                System.out.println("this is called every 5 seconds on UI thread");
                loadPowerTab();

            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

    }

    private void loadPowerTab() {

        final Service<String> SensorBuilder = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws InterruptedException, SQLException {

                        String output = "";
                        String temp_basic = "";
                        if (Powercounter % NumberOFPowersensors == 0 && frame_sensor == 0) {
                            temp_basic = "\n\nFrame " + (frame_index + 1);
                            temp_basic = temp_basic + "\n" + "Ground Station Date\\Time  = " + PowerGroundTime.get(frame_index);
                            temp_basic = temp_basic + "\n" + "OBC Date\\Time  = " + PowerOBCTime.get(frame_index);
                            temp_basic = temp_basic + "\n" + "Frame in Mode  = " + PowerMode.get(frame_index) + "\n";
                            //frame_sensor=1;
                            //Powercounter--;
                            output = temp_basic;//+Powerarray[Powercounter ] + " =" + temp_result + "\n";
                        } else {
                            updateProgress(0, 10);
                            String temp_result = Powerresult.getString(1);
                            System.out.println("Powercounter = " + (Powercounter) + " , --> " + Powerarray[Powercounter] + " =" + temp_result + "\n\n");

                            output = Powerarray[Powercounter] + " =" + temp_result + "\n";

                        }
                        if ((Powercounter % NumberOFPowersensors) - 1 == 0 && frame_sensor == 1) {
                            frame_sensor = 0;
                        }

                        return output;

                    }
                };
            }
        };

        System.out.println("SensorBuilder = " + SensorBuilder.toString());
        SensorBuilder.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue,
                    Worker.State oldState, Worker.State newState) {
                switch (newState) {
                    case SCHEDULED:
                        break;
                    case READY:
                    case RUNNING:
                        break;
                    case SUCCEEDED: {
                        try {
                            //Powerresult.next();
                            // System.out.println(counter +" "+Powerresult.getString(1));
                            //                        System.out.println("henA");
                            Label rec1 = new Label();
                            String split[] = SensorBuilder.valueProperty().getValue().split(" =");

                            if (SensorBuilder == null) {
                                rec1.setText("non");
                                //                              System.out.println("SensorBuilder.valueProperty().getValue() = " + "non");

                            } else {

                                rec1.setText(SensorBuilder.valueProperty().getValue());
//                                System.out.println("SensorBuilder.valueProperty().getValue() = " + SensorBuilder.valueProperty().getValue());

                            }

                            if (Powercounter % NumberOFPowersensors == 0 && frame_sensor == 0) {
                                loadingPower_frames[frame_index].textProperty().unbind();
                                rec1.setTextFill(Color.web("#008080"));
                                rec1.setStyle("-fx-font-weight: bold");

                                rec1.setFont(Font.font("Verdana", 14));
                                //                          System.out.println("frame number = "+frame_index+"blod");
                                frame_sensor = 1;
                                Powercounter--;
                                frame_index++;

                            } else {
                                Powerresult.next();
                                loadingPower[Powercounter].textProperty().unbind();
                                List list = Limits.get(Powerarray[Powercounter]);

                                double min = Double.parseDouble((String) list.get(5));
                                //System.out.println(min);
                                double max = Double.parseDouble((String) list.get(6));
                                if ((min != 0 || max != 0)) {
                                    // System.out.println("value " + split[1]);
                                    try {
                                        double value = Double.parseDouble(split[1]);
                                        if ((value < min || value > max)) {

                                            rec1.setTextFill(Color.web("#FF0000"));
                                        } else {
                                            rec1.setTextFill(Color.web("#010b17"));
                                        }
                                    } catch (Exception e) {
                                        //                            System.out.println("Cant check!");
                                    }
                                } else {
                                    rec1.setTextFill(Color.web("#010b17"));
                                }

                                rec1.setFont(Font.font("Verdana", 14));
                            }

                            loadPane1.getChildren().set(counter, rec1);
                            if (Powercounter < (NumberOFPowersensors * frame_number) - 1) {//NumberOFPowersensors-1 ) {
                                //                      System.out.println("************* power counter = "+Powercounter+" ********************");
                                Powercounter++;
                                nextPowerPane(SensorBuilder);

                            }
                            counter++;
                        } catch (SQLException ex) {
                            //                System.out.println("catchhhhhhhh");
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }
            }
        });

        nextPowerPane(SensorBuilder);
    }

    private void loadOBCTab() {

        final Service<String> SensorBuilder = new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws InterruptedException, SQLException {

                        updateProgress(0, 10);
                        for (int i = 0; i < 10; i++) {
                            //  Thread.sleep(10);
                        }

                        return OBCSensors[OBCcounter] + "  " + obcresult.getString(1) + "\n";

                    }
                };
            }
        };

        SensorBuilder.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue,
                    Worker.State oldState, Worker.State newState) {
                switch (newState) {
                    case SCHEDULED:
                        break;
                    case READY:
                    case RUNNING:
                        break;
                    case SUCCEEDED: {
                        try {
                            Powerresult.next();
                            // System.out.println(counter +" "+Powerresult.getString(1));
                            //              System.out.println("henA");
                            Label rec1 = new Label();
                            String split[] = SensorBuilder.valueProperty().getValue().split("  ");

                            rec1.setText(SensorBuilder.valueProperty().getValue() + "\n");
                            //            System.out.println("SensorBuilder.valueProperty().getValue() = " + SensorBuilder.valueProperty().getValue());
                            loadingOBC[OBCcounter].textProperty().unbind();
                            List list = Limits.get(OBCSensors[OBCcounter]);
                            double min = Double.parseDouble((String) list.get(5));
                            //System.out.println(min);
                            double max = Double.parseDouble((String) list.get(6));
                            if ((min != 0 || max != 0)) {
                                // System.out.println("value "+split[1]);
                                double value = Double.parseDouble(split[1]);
                                if ((value < min || value > max)) {

                                    rec1.setTextFill(Color.web("#FF0000"));
                                } else {
                                    rec1.setTextFill(Color.web("#008000"));
                                }
                            } else {
                                rec1.setTextFill(Color.web("#008000"));
                            }
                            rec1.setFont(Font.font("Verdana", 15));
                            OBCTile.getChildren().set(OBCcounter, rec1);
                            if (Powercounter < NumberOFPowersensors - 1) {
                                OBCcounter++;
                                nextPowerPane(SensorBuilder);

                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }
            }
        });

        nextOBCPane(SensorBuilder);
    }

    public void initializePowerTables() throws SQLException {

        PowerSensors = subsystem.setSensors("power");
        PowerMode = new ArrayList<>();
        PowerGroundTime = new ArrayList<>();
        PowerOBCTime = new ArrayList<>();

        //System.out.println("powerlength = " + PowerSensors.length);
        // System.out.println("***************** END ************************");
        //db.PACKETSENSORS("power",db.connectDB());
    }

    public void initializeOBCTables() throws SQLException {

        OBCSensors = subsystem.setSensors("OBC");

        //db.PACKETSENSORS("power",db.connectDB());
    }

    private void nextPowerPane(Service<String> recBuilder) {

        loadingPower[Powercounter].textProperty().bind(recBuilder.messageProperty());
        //System.out.println("here   --------> Powercounter = " + Powercounter);
        recBuilder.restart();
    }

    private void nextOBCPane(Service<String> recBuilder) {

        loadingOBC[OBCcounter].textProperty().bind(recBuilder.messageProperty());
        recBuilder.restart();
    }

    public void ReadPowerData() throws SQLException {
        int x = obj.getSessionID();
        Limits = obj.getSensors();
        Powerresult = subsystem.ReadData(x, "Power SubSystem");
        Powerresult1 = subsystem.ReadData(x, "Power SubSystem");
        //int i = 0;
        //System.out.println("**************** DB result *******************");
        //System.out.println("Session number = " + x);
        /*while (Powerresult1.next()) {
            i++;
          //  System.out.println(i + "-----> " + Powerresult1.getString(1) + " , ");// +Powerresult1.getString(3) + " , " +Powerresult1.getString(5) + " , "  );
        }*/
        Powerarray = new String[PowerSensors.length * frame_number];
        //Copying all elements of one array into another    
        int counter = 0;
        for (int i = 0; i < frame_number; i++) {
            for (int j = 0; j < PowerSensors.length; j++) {
                //     System.out.println("i + j "+(i+j)+" , i= "+i+"  , j = "+j +" , PowerSensors[j] = "+PowerSensors[j]);
                Powerarray[counter] = PowerSensors[j];
                counter++;
            }

        }
        /*for (int i = 0; i < Powerarray.length; i++) {
            System.out.println("i = " + i + " , value = " + Powerarray[i]);

        }*/

        Powerresult.next();
    }

    public void ReadOBCData() throws SQLException {
        int x = obj.getSessionID();
        OBCLimits = obj.getSensors();
        obcresult = subsystem.ReadData(x, "OBC SubSystem");
        obcresult.next();
    }

    private Node createLoadPane() throws SQLException {

        NumberOFPowersensors = 13;
        loadPane1 = loadPane;
        loadingPower = new Label[(NumberOFPowersensors * frame_number) + 1];
        for (int i = 0; i < (NumberOFPowersensors * frame_number); i++) {
            StackPane waitingPane1 = new StackPane();
            Label NewLabel = new Label();
            loadingPower[i] = NewLabel;
            waitingPane1.getChildren().addAll(loadingPower[i]);
            loadPane1.getChildren().add(waitingPane1);
            //  System.out.println("hello i = "+i);
        }

        loadingPower_frames = new Label[(frame_number) + 1];
        for (int i = 0; i < frame_number; i++) {
            StackPane waitingPane1 = new StackPane();
            Label NewLabel = new Label();
            loadingPower_frames[i] = NewLabel;
            waitingPane1.getChildren().addAll(loadingPower_frames[i]);
            loadPane1.getChildren().add(waitingPane1);
            //System.out.println("hello frame i = "+i);
        }

        //loadPane1.setTextAlignment(TextAlignment.CENTER);
        return loadPane1;
    }

    private Node createObcPane() throws SQLException {

        NumberOFOBCsensors = 16;
        //subsystem.getNumberOfSensors("power subsystem");
        //loadPane1 = new TilePane();
        loadingOBC = new Label[NumberOFPowersensors + 1];
        for (int i = 0; i < NumberOFPowersensors; i++) {
            StackPane waitingPane1 = new StackPane();
            waitingPane1.setMinSize(10, 20);
            waitingPane1.setMaxSize(100, 100);
            Label background = new Label();
            background.setText(i + "");
            loadingOBC[i] = new Label();
            waitingPane1.getChildren().addAll(background, loadingOBC[i]);
            OBCTile.getChildren().add(waitingPane1);
        }

        return OBCTile;
    }

    public String readFromfile(String fileName) throws IOException {
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

}
