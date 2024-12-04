/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author norhan
 */
public class UpdateFilesDistinationController implements Initializable {

    /**
     * Initializes the controller class.
     */
     public ListView listView;
     public ListView listView2;
     private String fileName1;
     private String fileName2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void saveIntofile(String fileName,String path) throws IOException{
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(path);
            bufferedWriter.close();
    } 
    public void buttom1(){
        fileName1 =buttomcode(listView);
        
    }
    public void buttom2(){
        fileName2 =buttomcode(listView2);
        
    }
    public void saveLiveFilePath() throws IOException{
         saveIntofile("livefile.txt",fileName1);
      }
    public void saveOfflineFilePath() throws IOException{
         saveIntofile("offlinefile.txt",fileName2);
         SelectNotification();
      }
    public String buttomcode (ListView listView ){
       
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(null);
                if(file !=null){
                listView.getItems().add(file.getName());
                }
                
                System.out.println(file);
                String fileName = file.toString();
                return fileName;
        
    }    
    
    public void SelectNotification(){
        Notifications notification = Notifications.create()
                .title("File Selected")
                .text("File Selected Successfully")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);
        notification.show();
                
                
        
    }
    
}
