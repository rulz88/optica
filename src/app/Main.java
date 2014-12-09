package app;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.*;
import static org.controlsfx.dialog.Dialog.Actions.NO;
import static org.controlsfx.dialog.Dialog.Actions.YES;

public class Main extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        Scene scene;
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.centerOnScreen();

        Parent root = load(getClass().getResource("gui/main.fxml"));
        // Especifica un icono a la ventana
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("gui/img/icon.png")));
        // Especifica el ttulo de la ventana
        primaryStage.setTitle("Monitor");
        scene = new Scene(root);
        primaryStage.setScene(scene);

        // Al cerrar con el boton de la ventana apaga el ejecutor de tareas (hilos)
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Controller.scheduleTaskExecutor.shutdown();
                // Salir de la aplicacion
                Platform.exit();
            }
        });

        // Listeners to handle mouse dragging
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        root.setOpacity(0);
        // Muestra la ventana
        primaryStage.show();

        FadeTransition ft = new FadeTransition(Duration.millis(1500), root);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();


        Process p1 = null;
        boolean reachable = false;
        try {
            p1 = Runtime.getRuntime().exec("ping www.google.com");
            int returnVal = p1.waitFor();
            reachable = (returnVal==0);

            System.out.println("Conexion a internet: " + (reachable? "Si" : "No"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        if(!reachable)
        {
            Dialogs diag = Dialogs.create()
                    .title("Confirmación")
                    .masthead(null)
                    .message("Sin Internet, ¿Desea permanecer en la red local?");
            configureSampleDialog(diag);
            diag.actions(new Action[]{YES, NO});
            Action response = diag.showConfirm();

            if(response.toString().equals("NO")) {
                // Salir de la aplicacion
                Platform.exit();
            }
            System.out.println("sin conexion a internet: ");

        }

    }

    private Dialogs configureSampleDialog(Dialogs dialog) {
        dialog.owner(null);
        dialog.lightweight();
        dialog.style(DialogStyle.CROSS_PLATFORM_DARK);
        return dialog;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
