package model2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class WriteExcel {
    
    String[] Sensors_name;//name
    String[] Sensors_session;
    String[] Sensors_frame;
    String[] Sensors_value;
    String[] Sensors_GroundTime;
    String[] Sensors_OBCTime;
    String[] Sensors_Mode;
    String[] Sensors_Time;
    String TabName;
    
     public  void WriteExcelFile() throws WriteException, IOException {
        try {
            File file = new File(TabName+".xls");
            if(TabName.equals("")){
                file = new File("output.xls");
            }
            WritableWorkbook wb = Workbook.createWorkbook(file);
            WritableSheet sht = wb.createSheet("data", 0);

            String[] x = {"Subsystem","Sensor Name", "Session", "Frame", "Value","Ground Time","OBC Time","Mode","Time"};
            /*
            String[] x = {"name", "Age", "Address", "year"};
            String[] y = {"ahmed", "mohmed", "ali", "twafeek"};
            String[] z = {"20", "50", "60", "30"};
            String[] n = {"maadi", "cairo", "giza", "helwan"};
            String[] m = {"2015", "2016", "2018", "2020"};*/

            sht.addCell(new Label(0, 0, x[0]));
            sht.addCell(new Label(1, 0, x[1]));
            sht.addCell(new Label(2, 0, x[2]));
            sht.addCell(new Label(3, 0, x[3]));
            sht.addCell(new Label(4, 0, x[4]));
            sht.addCell(new Label(5, 0, x[5]));
            sht.addCell(new Label(6, 0, x[6]));
            sht.addCell(new Label(7, 0, x[7]));
            sht.addCell(new Label(8, 0, x[8]));

            for (int i = 0; i < Sensors_name.length; i++) {
                sht.addCell(new Label(0, i + 1, "Power"));
                sht.addCell(new Label(1, i + 1, Sensors_name[i]));
                sht.addCell(new Label(2, i + 1, Sensors_session[i]));
                sht.addCell(new Label(3, i + 1, Sensors_frame[i]));
                sht.addCell(new Label(4, i + 1, Sensors_value[i]));
                sht.addCell(new Label(5, i + 1, Sensors_GroundTime[i]));
                sht.addCell(new Label(6, i + 1, Sensors_OBCTime[i]));
                sht.addCell(new Label(7, i + 1, Sensors_Mode[i]));
                sht.addCell(new Label(8, i + 1, Sensors_Time[i]));
 
            }

            wb.write();
            wb.close();
            System.out.println("Workbook is created");
        } catch (FileNotFoundException e) {
           JFrame frame = new JFrame("JOptionPane showMessageDialog example");
    
    // show a joptionpane dialog using showMessageDialog
    JOptionPane.showMessageDialog(frame,"Problem writing to backup directory: '" + e.getMessage()+ "'.");
        }
    }
    
}
