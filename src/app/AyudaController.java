package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rosa Brena on 03/12/2014.
 */
public class AyudaController implements Initializable {

    @FXML private Button salir;
    private String helpdoc = "manual.pdf";

    @FXML
    private void getOut(MouseEvent event) {
        //System.out.println("press");
        Node node = (Node) event.getSource();
        String str;
        do{
            node = node.getParent();
            str = node.getId();
            System.out.println();
        }
        while(!str.equals("Acerca"));
        Stage stg = (Stage) node.getScene().getWindow();
        //principal.start(new javafx.stage.Stage());
        stg.close();
    }

    @FXML
    private  void  abrirManual(MouseEvent event){
        try {
            Desktop.getDesktop().open(new File("manual.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
