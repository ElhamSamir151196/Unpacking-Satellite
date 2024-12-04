/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import com.jfoenix.controls.JFXToggleButton;
import java.awt.GridLayout;
import static java.awt.SystemColor.menu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.controlsfx.control.Notifications;

/**
 *
 * @author norhan
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    public AnchorPane root;
    @FXML
    public JFXToggleButton MOOD;

    @FXML

    public void loadInitialization() throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("initialization_1.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);

        } catch (Exception e) {

        }
    }

    public void loadDesignedTabs() throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("ShowDesignedTabs.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);

        } catch (Exception e) {

        }
    }

    public void LoadUpdateFilesDirectory() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UpdateFilesDistination.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void ChangeDBSetting() throws IOException {
        JFrame frame;
        JPanel pane;
        JTextField daysField, assignmentField;
        // String days ="", assignments ="";

        frame = new JFrame("My Frame's Title");
        pane = new JPanel();
        pane.setLayout(new GridLayout(0, 2, 2, 2));
        daysField = new JTextField(5);
        assignmentField = new JTextField(5);

        pane.add(new JLabel("User Name:"));
        pane.add(daysField);
        pane.add(new JLabel("Password :"));
        pane.add(assignmentField);

        int option = JOptionPane.showConfirmDialog(frame, pane, "Please fill DataBase fields", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {

            String UserNameDB = daysField.getText();
            String PasswordDB = assignmentField.getText();

            if (UserNameDB.isEmpty() || PasswordDB.isEmpty()) {
                JOptionPane.showConfirmDialog(frame, "Please fill All DataBase fields");
            } else {
                   
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = null;
                    String dbURL = "jdbc:mysql://localhost:3306/cubesat_copy";
                    connection = DriverManager.getConnection(dbURL, UserNameDB, PasswordDB);
                    pane = new JPanel();
                    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
                    pane.add(new JLabel("Connection Done Sucessfully"));
                    JOptionPane.showMessageDialog(frame, pane);
                    ///// Write in file 
                    String txt=UserNameDB+","+PasswordDB;
                    saveIntofile("fileDB.txt",txt);
                } catch (ClassNotFoundException ex) {
                    pane = new JPanel();
                    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
                    pane.add(new JLabel("user name: " + UserNameDB));
                    pane.add(new JLabel("password : " + PasswordDB));
                    pane.add(new JLabel("Connection Error!"));
                    JOptionPane.showMessageDialog(frame, pane);
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    pane = new JPanel();
                    pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
                    pane.add(new JLabel("user name: " + UserNameDB));
                    pane.add(new JLabel("password : " + PasswordDB));
                    pane.add(new JLabel("Connection Error!"));
                    JOptionPane.showMessageDialog(frame, pane);
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                }

            }

        }

    

    public void loadCharts() throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Charts.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);
        } catch (Exception e) {

        }
    }

    public void loadDesignNewTab() throws IOException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("ShowHistory.fxml"));
            root.getChildren().clear();
            root.getChildren().addAll(pane);
        } catch (Exception e) {

        }
    }

    public void loadBeginUnpackingProcess() throws IOException {

        try {
            // String a = readIntofile("livefile.txt");
            String b = readIntofile("offlinefile.txt");
            if (b.length() <= 0) {

                SelectNotification();

            } else {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("BeginUnpackingProcess.fxml"));
                root.getChildren().clear();
                root.getChildren().addAll(pane);
            }
        } catch (Exception e) {
            SelectNotification();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            saveIntofile("mood.txt", "off");

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ChangeMood() throws IOException {
        saveIntofile("mood.txt", "off");      
    }

    public void saveIntofile(String fileName, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(path);
        bufferedWriter.close();
    }

    public String readIntofile(String fileName) throws IOException {
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

    public void SelectNotification() {
        Notifications notification = Notifications.create()
                .title("Select file first")
                .text("File directory must be selected before begin unpacking process")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);
        notification.show();

    }
    
    
}
