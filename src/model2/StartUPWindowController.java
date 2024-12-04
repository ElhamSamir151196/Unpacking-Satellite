/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author norhan
 */
public class StartUPWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private AnchorPane StartUp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        splashScreen s =new splashScreen();
        s.start();
    }    
    class splashScreen extends Thread{
        
        public void run(){
            try {
                Thread.sleep(5000);
                  Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                  Scene scene = new Scene(root);
                  Stage stage = new Stage();
                  stage.setScene(scene);
                  stage.show();
                   StartUp.getScene().getWindow().hide();
            } catch (InterruptedException ex) {
                Logger.getLogger(StartUPWindowController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StartUPWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
