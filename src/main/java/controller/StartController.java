package controller;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Field;
import model.Generation;
import view.FieldSettingsView;
import view.SimulationView;
import view.StartView;
import view.components.FieldView;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for StartView.
 */
public class StartController {
    public static Logger logger = Logger.getLogger(StartController.class.getName());
    private static boolean enableLogging = true;

    static {
        logger.setLevel(Level.ALL);
    }

    private final StartView view;
    private final Stage stage;

    /**
     * Constructor of object.
     *
     * @param view  view of controller.
     * @param stage primary stage.
     */
    public StartController(StartView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        view.getCustomSimulationButton().setOnMouseClicked(mouseEvent -> onCustomSimulationButtonClick());

        view.getPresetSimulationButton().setOnMouseClicked(mouseEvent -> onPresetSimulationButtonClick());

        view.getExitButton().setOnMouseClicked(mouseEvent -> onExitButtonClick());

        view.getLoggingCheckBox().setOnMouseClicked(mouseEvent -> onLoggingCheckBoxClick());

        logger.log(Level.INFO, "Start page set up");
    }

    /**
     * @return is logging enabled.
     */
    public static boolean isEnableLogging() {
        return enableLogging;
    }

    /**
     * Custom simulation button handler react.
     */
    private void onCustomSimulationButtonClick() {
        logger.log(Level.INFO, "Custom simulation button clicked");

        FieldSettingsView fieldSettingsView = new FieldSettingsView();
        FieldSettingsController fieldSettingsController = new FieldSettingsController(fieldSettingsView, stage);

        Scene mapSettingsScene = new Scene(fieldSettingsView, 1280, 720);

        stage.setScene(mapSettingsScene);
        stage.show();
    }

    /**
     * Preset simulation button handler react.
     */
    private void onPresetSimulationButtonClick() {
        logger.log(Level.INFO, "Preset simulation button clicked");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose configuration file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        String filePath;

        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
            logger.log(Level.INFO, "File chosen successfully");
        } else {
            return;
        }

        Field field = Field.loadConfiguration(filePath);

        field.placeBots(Generation.getNewGeneration(field.getBotCount(), field.getBotMaxHealth() / 2));
        field.placeEdibleCells();

        FieldView fieldView = new FieldView(field, 840, 522);
        SimulationView simulationView = new SimulationView(fieldView);
        SimulationController simulationController = new SimulationController(simulationView, stage);

        Scene simulationScene = new Scene(simulationView, 1280, 720);
        stage.setScene(simulationScene);
        stage.show();
    }

    /**
     * Exit button handler react.
     */
    private void onExitButtonClick() {
        logger.log(Level.INFO, "Exit button clicked");
        stage.close();
    }

    /**
     * Logging checkbox handler react.
     */
    private void onLoggingCheckBoxClick() {
        enableLogging = view.getLoggingCheckBox().isSelected();
        if (enableLogging) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.OFF);
        }
    }
}
