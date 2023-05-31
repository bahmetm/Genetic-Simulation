package application;

import controller.StartController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.StartView;

public class MainApp extends Application {
    public static void main(String[] args) {
        Application.launch();
    }


    @Override
    public void start(Stage primaryStage) {
        StartView startView = new StartView();
        StartController startController = new StartController(startView, primaryStage);

        Scene startScene = new Scene(startView, 1280, 720);
        primaryStage.setScene(startScene);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.setTitle("Genetic Simulation");
        primaryStage.show();
    }
}
