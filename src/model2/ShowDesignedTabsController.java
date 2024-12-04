/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.script.ScriptException;
import jxl.write.WriteException;
import static model2.DesignNewTabController.dataFormat1;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author norhan
 */
public class ShowDesignedTabsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane root;
    @FXML
    public TableView<TabInfo> tableView;
    @FXML
    public TableColumn<TabInfo, String> nameCol;
    DB db = new DB();
    ArrayList<TabInfo> Tabs = new ArrayList<TabInfo>();
    private int NumberOFPowersensors;
    private String PowerSensors[];
    @FXML
    public TilePane loadPane1;
    public GridPane gride;
    private Label loadingPower[];
    private int count = 0;
    Subsystem subsystem = new Subsystem();
    unpacking obj;
    ResultSet Powerresult;
    private Map<String, List<String>> Limits;
    private ArrayList<ResultSet> values = new ArrayList<ResultSet>();

    @FXML
    private ListView<String> listView;
    public static DataFormat dataFormat2 = new DataFormat("mydata2");
    @FXML
    public TableColumn<Sensor, String> subsystemCol;

    @FXML
    public TableColumn<Sensor, String> SessionCol;

    @FXML
    public TableColumn<Sensor, String> FrameCol;

    @FXML
    public TableColumn<Sensor, String> SensorCol;

    @FXML
    public TableColumn<Sensor, String> ValueCol;

    @FXML
    public TableColumn<Sensor, String> ModeCol;

    @FXML
    public TableColumn<Sensor, String> GroundTimeCol;

    @FXML
    public TableColumn<Sensor, String> OBCTimeCol;

    @FXML
    public TableColumn<Sensor, String> TimeCol;

    @FXML
    public TableColumn<Sensor, String> IndexCol;
    @FXML
    public TableView<Sensor> tableView1;

    String[] Sensors;
    String[] Sensors_name;//name
    String[] Sensors_session;
    String[] Sensors_frame;
    String[] Sensors_value;
    String[] Sensors_GroundTime;
    String[] Sensors_OBCTime;
    String[] Sensors_Mode;
    String[] Sensors_Time;
    ObservableList<Integer> selectedIndexes = FXCollections.observableArrayList();
    ObservableList<Integer> selectedIndexes1 = FXCollections.observableArrayList();
    ObservableList<String> selectedSensors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert nameCol != null;
        assert tableView != null;
        assert tableView1 != null;
        assert subsystemCol != null;
        assert SessionCol != null;
        assert FrameCol != null;
        assert SensorCol != null;
        assert ValueCol != null;
        assert ModeCol != null;
        assert GroundTimeCol != null;
        assert OBCTimeCol != null;
        assert TimeCol != null;
        assert IndexCol != null;

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // set cell value factories
        setCellValueFactories();

        try {

            tableView.setItems(initializeTable());
            //change listview observable list
            tableView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Integer> change) {
                    selectedIndexes.setAll(change.getList());

                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(ShowDesignedTabsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //   listView.setItems(selectedSensors);
    }

    public ObservableList<TabInfo> initializeTable() throws SQLException {
        ObservableList<TabInfo> tabs = FXCollections.observableArrayList();
        System.out.println("henea");
        ResultSet result = db.ShowUserTabs(db.connectDB());
        while (result.next()) {
            TabInfo t = new TabInfo();
            t.setName(result.getString(1));
            t.setSensors(result.getString(2));
            t.setSubSystem(result.getString(3));
            t.setFrame(result.getString(4));
            t.setSession(result.getString(5));
            t.setSensorValue(result.getString(6));
            t.setGrondTme(result.getString(7));
            t.setObcTime(result.getString(8));
            t.setMode(result.getString(9));
            t.setTime(result.getString(10));
            Tabs.add(t);
            tabs.add(t);
            System.out.println("aho " + tabs.get(0));
        }
        System.out.println("tabs length = " + tabs.size());

        return tabs;

    }

    public void loadHome() throws IOException {
        try {
            // System.out.println("eh b2a");
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);

        } catch (Exception e) {
            System.out.println("model2.ShowDesignedTabsController.loadHome()");
        }
    }

    @FXML
    private void Show() throws SQLException {
        /**
         *
         */

        if (selectedIndexes.size() > 0) {
            /*
            Notifications notification = Notifications.create()
                    .title("Tab Selected")
                    .text("index selected is "+selectedIndexes.get(0))
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();*/
            getSelected();
            tableView1.setItems(getData());

        } else {
            Notifications notification = Notifications.create()
                    .title("Tab Selected")
                    .text("Select Tab please!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
        }

    }

    @FXML
    private void WriteExcel() throws SQLException, WriteException, IOException {
        /**
         *
         */

        if (selectedIndexes.size() > 0) {
            getSelected();
            getData();
            WriteExcel ExcelFile = new WriteExcel();

            ExcelFile.Sensors_name = Sensors_name;//name
            ExcelFile.Sensors_session = Sensors_session;
            ExcelFile.Sensors_frame = Sensors_frame;
            ExcelFile.Sensors_value = Sensors_value;
            ExcelFile.Sensors_GroundTime = Sensors_GroundTime;
            ExcelFile.Sensors_OBCTime = Sensors_OBCTime;
            ExcelFile.Sensors_Mode = Sensors_Mode;
            ExcelFile.Sensors_Time = Sensors_Time;
            ExcelFile.TabName=Tabs.get(selectedIndexes.get(0)).getName();
            ExcelFile.WriteExcelFile();
            Notifications notification = Notifications.create()
                    .title("Tab Excel")
                    .text("File Written Successfully")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();

        } else {
            Notifications notification = Notifications.create()
                    .title("Tab Selected")
                    .text("Select Tab please!")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
        }

    }

    public ObservableList<Sensor> getData() {

        ObservableList<Sensor> sensors = FXCollections.observableArrayList();
        for (int i = 0; i < Sensors_name.length; i++) {
            Sensor p = new Sensor();

            p.setSubsystem("Power");
            p.setName(Sensors_session[i]);//session numbers
            p.setFrame(Sensors_frame[i]);
            p.setSensor(Sensors_name[i]);//sensor name
            p.setValue(Sensors_value[i]);
            p.setGroundTime(Sensors_GroundTime[i]);
            p.setOBCTime(Sensors_OBCTime[i]);
            p.setMode(Sensors_Mode[i]);
            p.setTime(Sensors_Time[i]);
            p.setID(i + 1);
            sensors.add(p);
        }
        return sensors;
    }

    public void getSelected() {

        System.out.println("henea");
        int index_selection = selectedIndexes.get(0);
        System.out.println("index_selection = " + index_selection);
        System.out.println("Tabs length = " + Tabs.size());

        String temp_sensors = Tabs.get(index_selection).getSensors();
        Sensors_name = temp_sensors.split(",");

        String Temp_session = Tabs.get(index_selection).getSession();
        Sensors_session = Temp_session.split(",");

        String Temp_frame = Tabs.get(index_selection).getFrame();
        Sensors_frame = Temp_frame.split(",");

        String Temp_values = Tabs.get(index_selection).getSensorValue();
        Sensors_value = Temp_values.split(",");

        String Temp_Ground_time = Tabs.get(index_selection).getGrondTme();
        Sensors_GroundTime = Temp_Ground_time.split(",");

        String Temp_obc_time = Tabs.get(index_selection).getObcTime();
        Sensors_OBCTime = Temp_obc_time.split(",");

        String temp_Mode = Tabs.get(index_selection).getMode();
        Sensors_Mode = temp_Mode.split(",");

        String temp_time = Tabs.get(index_selection).getTime();
        Sensors_Time = temp_time.split(",");

    }

    private void setCellValueFactories() {
        subsystemCol.setCellValueFactory(new PropertyValueFactory("Subsystem"));
        SessionCol.setCellValueFactory(new PropertyValueFactory("Name"));//session nmber
        FrameCol.setCellValueFactory(new PropertyValueFactory("Frame"));
        SensorCol.setCellValueFactory(new PropertyValueFactory("Sensor"));
        ValueCol.setCellValueFactory(new PropertyValueFactory("Value"));
        ModeCol.setCellValueFactory(new PropertyValueFactory("Mode"));
        GroundTimeCol.setCellValueFactory(new PropertyValueFactory("GroundTime"));
        OBCTimeCol.setCellValueFactory(new PropertyValueFactory("OBCTime"));
        TimeCol.setCellValueFactory(new PropertyValueFactory("Time"));
        IndexCol.setCellValueFactory(new PropertyValueFactory("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory("Name"));

    }

    public void setRowSelection() {
        tableView.getSelectionModel().clearSelection();
        tableView.getSelectionModel().setCellSelectionEnabled(false);
    }

    private void setSelection(IndexedCell cell) {
        if (cell.isSelected()) {
            System.out.println("False");
            tableView.getSelectionModel().clearSelection(cell.getIndex());
        } else {
            System.out.println("true");
            tableView.getSelectionModel().select(cell.getIndex());
        }

    }
}
