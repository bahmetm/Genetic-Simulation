package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.FieldSettingsView;
import view.SimulationSettingsView;
import view.StartView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for FieldSettingsView.
 */
public class FieldSettingsController {
    private FieldSettingsView view;
    private Stage stage;
    public static Logger logger = Logger.getLogger(FieldSetupController.class.getName());
    static {
        if (StartController.isEnableLogging()) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.OFF);
        }
    }

    /**
     * Constructor of object.
     * @param view view of controller.
     * @param stage primary stage.
     */
    public FieldSettingsController(FieldSettingsView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        view.getContinueButton().setOnAction(actionEvent -> onContinueButtonClick());
        view.getBackButton().setOnAction(actionEvent -> onBackButtonClick());

        logger.log(Level.INFO, "Field setting set up");
    }

    /**
     * Continue button handler event.
     */
    private void onContinueButtonClick() {
        logger.log(Level.INFO, "Continue button clicked");

        int fieldWidth = (int) view.getWidthSlider().getValue();
        int fieldHeight = (int) view.getHeightSlider().getValue();
        int botCount = (int) view.getBotCountChoiceBox().getValue();

        SimulationSettingsView simulationSettingsView = new SimulationSettingsView(fieldWidth, fieldHeight, botCount);
        SimulationSettingsController simulationSettingsController = new SimulationSettingsController(simulationSettingsView, stage);

        Scene simulationSettingsScene = new Scene(simulationSettingsView, 1280, 720);

        stage.setScene(simulationSettingsScene);
        stage.show();
    }

    /**
     * Back button handler event.
     */
    private void onBackButtonClick() {
        logger.log(Level.INFO, "Back button clicked");

        StartView startView = new StartView();
        StartController startController = new StartController(startView, stage);

        Scene startViewScene = new Scene(startView, 1280, 720);

        stage.setScene(startViewScene);
        stage.show();
    }
}
