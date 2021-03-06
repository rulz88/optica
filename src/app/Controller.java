package app;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.HiddenSidesPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javafx.fxml.FXMLLoader.load;
import static org.controlsfx.dialog.Dialog.Actions.NO;
import static org.controlsfx.dialog.Dialog.Actions.YES;

public class Controller implements Initializable {

    // Ejecutor de Tareas con un hilo
    final static ScheduledExecutorService
            scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

    // Objetos FXML
    @FXML private AnchorPane ap;
    @FXML private Pane containerPane;

    @FXML private Button info;
    @FXML private Button temp;
    @FXML private Button pre;
    @FXML private Button stat;
    @FXML private Button set;
    @FXML private Button help;
    @FXML private Button close;

    @FXML private WebView browser;
    @FXML private Label clock;

    //Variables para las graficas
    @FXML private TextField rangoMin;
    @FXML private TextField rangoMax;
    @FXML private ChoiceBox choiceFun;
    @FXML private Button button;

    // Declaramos el "LineChart" donde pintaremos la funcion
    //@FXML private LineChart<Double, Double> graph;
    //@FXML private AnchorPane chartArea;
    protected Stage acercade;
    private String url = "http://unistmosolar.url.ph/Optica/plot.html";
    private WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Programa el hilo para acutalizar un label cada segundo
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { clock.setText(LocalTime.now().toString()); }
                });
            }
        }, 0, 1, TimeUnit.SECONDS);

        //Grafica plotly
        webEngine = browser.getEngine();
        webEngine.load(url);
        //webEngine.load("http://www.google.com");

        // Panel para el Acerca De
        acercade = new Stage();
        Scene scene;
        acercade.setResizable(false);
        acercade.initStyle(StageStyle.UNDECORATED);
        acercade.centerOnScreen();
        Parent root = null;
        try {
            root = load(getClass().getResource("gui/acerca.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        root.setOpacity(.25);
        scene = new Scene(root);
        acercade.setScene(scene);

        HiddenSidesPane hiddenSidesPane = new HiddenSidesPane();


        SideNode left = new SideNode("Left", Side.LEFT, hiddenSidesPane);
        left.setStyle("-fx-background-color: rgba(0,0,0,.25);");
        left.setText("texto texto texto");
        hiddenSidesPane.setLeft(left);
        hiddenSidesPane.setPrefSize(200, 570);
        containerPane.getChildren().add(hiddenSidesPane);

        /*CurvedFittedAreaChart chart = new CurvedFittedAreaChart(
                new NumberAxis(0,20000,2500), new NumberAxis(0,1000,200));
        chart.setLegendVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setAlternativeRowFillVisible(false);
        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.getData().addAll(
                new XYChart.Data<Number,Number>(0       ,950),
                new XYChart.Data<Number,Number>(1000    ,570),
                new XYChart.Data<Number,Number>(2000    ,100),
                new XYChart.Data<Number,Number>(3500    ,140),
                new XYChart.Data<Number,Number>(5000    ,200),
                new XYChart.Data<Number,Number>(7500    ,180),
                new XYChart.Data<Number,Number>(10000   ,100)
        );
        chart.getData().add(series);
        chartArea.getChildren().add(chart);*/

        // Change the data over time to represent real live data
        /*Timeline dataModification = new Timeline();
        dataModification.setCycleCount(Timeline.INDEFINITE);
        dataModification.getKeyFrames().setAll(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            Random r = new Random();
            @Override public void handle(ActionEvent arg0) {
                ObservableList<XYChart.Data<Number, Number>> data = series.getData();
                int dataIndex = r.nextInt(data.size());
                XYChart.Data<Number, Number> dataItem = data.get(dataIndex);
                dataItem.setYValue(Math.min(Math.max(dataItem.getYValue().intValue() + r.nextInt(200) - 100, 100), 1000));
            }
        }));
        dataModification.play();*/
    }

    @FXML
    private void btnHover(MouseEvent mouseEvent) {
        // Obtiene el objeto que generó el evento (Button)
        Button btn = (Button) mouseEvent.getSource();
        String id = btn.getId();
        String src = "";
        // Obtiene su imagen
        ImageView iv = (ImageView) btn.getGraphic();

        // Cada sentencia if verifica los id
        if(id.equals("stat")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/stat2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/stat.png";
        }

        if(id.equals("temp")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/temp2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/temp.png";
        }

        if(id.equals("pre")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/pressure2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/pressure.png";
        }

        if(id.equals("set")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/settings2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/settings.png";
        }

        if(id.equals("help")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/help2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/help.png";
        }

        if(id.equals("info")) {
            // Segun si el mouse entra o sale del boton cambia el icono
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/info2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/info.png";
        }

        if(id.equals("close")) {
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED)
                src = "gui/img/exit2.png";
            else if(mouseEvent.getEventType() == MouseEvent.MOUSE_EXITED)
                src = "gui/img/exit.png";
        }

        // Cambia el icono al que esta en la ruta especificada por 'src'
        iv.setImage(new Image(getClass().getResourceAsStream(src)));
    }

    @FXML
    private void infoClicked(MouseEvent mouseEvent) throws IOException {

        Scene scene;
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        Parent root = load(getClass().getResource("gui/acerca.fxml"));
        stage.setTitle("Acerca De");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gui/img/info.png")));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void tempClicked(MouseEvent mouseEvent) {
        //TODO : Functionality
        url = "http://unistmosolar.url.ph/Optica/plot.html";
        webEngine.load(url);
    }

    @FXML
    private void pressureClicked(MouseEvent mouseEvent) {
        //TODO : Functionality
        System.out.println("press");
    }

    @FXML
    private void statClicked(MouseEvent mouseEvent) {
        //TODO : Functionality
        url = "http://unistmosolar.url.ph/Optica/stat.php";
        webEngine.load(url);
    }

    @FXML
    private void settingsClicked(MouseEvent mouseEvent)  throws IOException {
        //TODO : Functionality
        System.out.println("Datos");
        Scene scene;
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        Parent root = load(getClass().getResource("gui/datos.fxml"));
        stage.setTitle("Datos");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gui/img/help.png")));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void helpClicked(MouseEvent mouseEvent)  throws IOException {
        //TODO : Functionality
        System.out.println("help");
        Scene scene;
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        Parent root = load(getClass().getResource("gui/ayuda.fxml"));
        stage.setTitle("Ayuda");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("gui/img/help.png")));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Dialogs configureSampleDialog(Dialogs dialog) {
        dialog.owner(null);
        dialog.lightweight();
        dialog.style(DialogStyle.CROSS_PLATFORM_DARK);
        return dialog;
    }

    @FXML
    private void exitClicked(MouseEvent mouseEvent) {
        // Al cerrar apaga el ejecutor de tareas (hilos)
        scheduleTaskExecutor.shutdown();

        // Construye un dialogo de confirmacion
        Action response = configureSampleDialog(
                Dialogs.create()
                        .title("Confirmación")
                        .masthead(null)
                        .message(
                                "¿Está seguro que desea salir?")
        )
                .actions(new Action[]{YES, NO})
                .showConfirm();

        //System.out.println("response: " + response);
        if(response.toString().equals("YES")) {
            // Salir de la aplicacion
            Platform.exit();
        }
    }

   /* @FXML
    private void conexionClicked(MouseEvent mouseEvent) {
        // Al cerrar apaga el ejecutor de tareas (hilos)
       scheduleTaskExecutor.shutdown();

        // Construye un dialogo de confirmacion
        Action response = configureSampleDialog(
                Dialogs.create()
                        .title("Confirmación")
                        .masthead(null)
                        .message(
                                "Sin Internet, ¿Desea permanecer en la red local?")
        )
                .actions(new Action[]{YES, NO})
                .showConfirm();

        //System.out.println("response: " + response);
        if(response.toString().equals("NO")) {
            // Salir de la aplicacion
            Platform.exit();
        }*/

    class SideNode extends Label {

        public SideNode(final String text, final Side side,
                        final HiddenSidesPane pane) {

            super(text + " (Click to pin / unpin)");

            setAlignment(Pos.CENTER);
            setPrefSize(150, 200);

            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (pane.getPinnedSide() != null) {
                        setText(text + " (unpinned)");
                        pane.setPinnedSide(null);
                    } else {
                        setText(text + " (pinned)");
                        pane.setPinnedSide(side);
                    }
                }
            });
        }
    }
}
