/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexedCell;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author norhan
 */
public class DesignNewTabController implements Initializable {

    @FXML
    private ListView<String> listView;
    public static DataFormat dataFormat1 = new DataFormat("mydata1");
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
    public TableView<Sensor> tableView;
    @FXML
    public TextField TabName;
    public AnchorPane root;
    DB db = new DB();
    String[] Sensors;
    ArrayList<String> Sensors_name;//name
    ArrayList<String> Sensors_session;
    ArrayList<String> Sensors_frame;
    ArrayList<String> Sensors_value;
    ArrayList<String> Sensors_GroundTime;
    ArrayList<String> Sensors_OBCTime;
    ArrayList<String> Sensors_Mode;
    ArrayList<String> Sensors_Time;
    ObservableList<Integer> selectedIndexes = FXCollections.observableArrayList();
    ObservableList<String> selectedSensors = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        // changed to multiple selection mode
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // set cell value factories
        setCellValueFactories();

        //set Dummy Data for the TableView
        tableView.setItems(getData());

        //ListView items bound with selection index property of tableview
        listView.setItems(selectedSensors);

        //change listview observable list
        tableView.getSelectionModel().getSelectedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Integer> change) {
                selectedIndexes.setAll(change.getList());
                selectedSensors.clear();
                for (int i = 0; i < selectedIndexes.size(); i++) {
                    selectedSensors.add((selectedIndexes.get(i)+1)+"");//Sensors_name.get(selectedIndexes.get(i)));
                }

            }
        });

        //set the Row Factory of the table
        setRowFactory();

        //Set row selection as default
        setRowSelection();
    }

    public void loadHome() throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);
        } catch (Exception e) {

        }
    }

    @FXML
    private void Store(ActionEvent event) throws SQLException {
        if (selectedIndexes.size() > 0) {
            String[] UserTabs;
            String Table = db.TabsName("Power", db.connectDB());
            System.out.println("result = " + Table);
            UserTabs = Table.split(",");
            boolean find = false;
            String toCheckValue = TabName.getText();
            for (String element : UserTabs) {
                if (element.equals(toCheckValue)) {
                    find = true;
                    break;
                }
            }
            System.out.println("find ====== "+find +" ,, TabName.getText() = "+TabName.getText());
            if(TabName.getText().equals("") ){
                Notifications notification = Notifications.create()
                        .title("Sensors Selected")
                        .text("Write User Tab to store")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.CENTER);
                notification.show();
            }else if (find == true) {
                Notifications notification = Notifications.create()
                        .title("Sensors Selected")
                        .text("User Tab already exist")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.CENTER);
                notification.show();
            }else if (!TabName.getText().isEmpty() && find == false) {

                String Session = "", Frame = "", SensorName = "", SensorValue = "", Time = "", GrondTme = "", ObcTime = "", Mode = "", Subsystem = "Power";
                for (int i = 0; i < selectedSensors.size(); i++) {
                    SensorName += Sensors_name.get(selectedIndexes.get(i)) + ',';
                    Frame += Sensors_frame.get(selectedIndexes.get(i)) + ',';
                    Session += Sensors_session.get(selectedIndexes.get(i)) + ',';
                    SensorValue += Sensors_value.get(selectedIndexes.get(i)) + ',';
                    Time += Sensors_Time.get(selectedIndexes.get(i)) + ',';
                    GrondTme += Sensors_GroundTime.get(selectedIndexes.get(i)) + ',';
                    ObcTime += Sensors_OBCTime.get(selectedIndexes.get(i)) + ',';
                    Mode += Sensors_Mode.get(selectedIndexes.get(i)) + ',';
                }

                db.Insertusertab(Subsystem, SensorName, Frame, Session, SensorValue, Time, GrondTme, ObcTime, Mode, TabName.getText(), db.connectDB());
               
            } else {
                Notifications notification = Notifications.create()
                        .title("Sensors Selected")
                        .text("Write User Tab to store")
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.CENTER);
                notification.show();
            }
        } else {
            Notifications notification = Notifications.create()
                    .title("Session Selected")
                    .text("Select Session please.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.CENTER);
            notification.show();
        }
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

    }

    public void setRowFactory() {
        tableView.setRowFactory(new Callback<TableView<Sensor>, TableRow<Sensor>>() {
            @Override
            public TableRow<Sensor> call(TableView<Sensor> p) {
                final TableRow<Sensor> row = new TableRow<Sensor>();
                row.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent t) {
                        setSelection(row);
                    }
                });

                row.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {

                        Dragboard db = row.getTableView().startDragAndDrop(TransferMode.COPY);
                        ClipboardContent content = new ClipboardContent();
                        content.put(dataFormat1, "XData");
                        db.setContent(content);
                        setSelection(row);
                        t.consume();

                    }
                });
                return row;
            }
        });
    }

    public void initializeTable() throws SQLException {
        System.out.println("henea");
        Sensors_name = new ArrayList<>();//name
        Sensors_session = new ArrayList<>();
        Sensors_frame = new ArrayList<>();
        Sensors_value = new ArrayList<>();
        Sensors_GroundTime = new ArrayList<>();
        Sensors_OBCTime = new ArrayList<>();
        Sensors_Mode = new ArrayList<>();
        Sensors_Time = new ArrayList<>();

        String Table = db.PACKETSENSORS("Power", db.connectDB());
        System.out.println("result = " + Table+"================= hena ya elham");
        Sensors = Table.split(",");

        ResultSet result = db.SESSIONSELECTED("Power", db.connectDB());
        int index=0;
        while (result.next()) {

            System.out.println("index = "+index+"******");
            Sensors_name.add(Sensors[result.getInt("SENSORNO") - 1]);
            Sensors_session.add(result.getString("sessionID"));
            Sensors_frame.add(result.getString("Frame"));
            Sensors_value.add(result.getString("VALUE"));
            Sensors_GroundTime.add(result.getString("Ground_Time"));
            Sensors_OBCTime.add(result.getString("OBC_Time"));
            Sensors_Mode.add(result.getString("Mode"));
            Sensors_Time.add(result.getString("Time"));

            index++;
        }
       // System.out.println(result);
        //Sensors = result.split(",");

    }

    public ObservableList<Sensor> getData() {
        //System.out.println("errorrrrrrrrrrrrrrrrrrrrr");
        try {

            initializeTable();
        } catch (SQLException ex) {
            System.out.println("error is "+ex.getMessage());
            Logger.getLogger(DesignNewTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Sensor> sensors = FXCollections.observableArrayList();
        for (int i = 0; i < Sensors_name.size(); i++) {
            Sensor p = new Sensor();

            p.setSubsystem("Power");
            p.setName(Sensors_session.get(i));//session number
            p.setFrame(Sensors_frame.get(i));
            p.setSensor(Sensors_name.get(i));//sensor name
            p.setValue(Sensors_value.get(i));
            p.setGroundTime(Sensors_GroundTime.get(i));
            p.setOBCTime(Sensors_OBCTime.get(i));
            p.setMode(Sensors_Mode.get(i));
            p.setTime(Sensors_Time.get(i));
            p.setID(i + 1);
            sensors.add(p);
        }
        return sensors;
    }

}
