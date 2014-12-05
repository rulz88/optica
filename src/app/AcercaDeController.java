package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rosa Brena on 03/12/2014.
 */
public class AcercaDeController implements Initializable {

    @FXML private Button salir;
    private String pag_lab = "http://www.unistmo.edu.mx/~laboptica";

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
    private  void  link(){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(pag_lab));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Created by Rosa Brena on 03/12/2014.
     */
    public static class AyudaController implements Initializable {

        @FXML private Button salir;
        private String pag_lab = "http://www.unistmo.edu.mx/~laboptica";

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
        private  void  link(){
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(pag_lab));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }
    }
}
