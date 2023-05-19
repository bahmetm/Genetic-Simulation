package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Field;
import view.FieldSettingsView;
import view.FieldSetupView;
import view.SimulationSettingsView;
import view.SimulationView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for SimulationSettingsView.
 */
public class SimulationSettingsController {
    private SimulationSettingsView view;
    private Stage stage;

    public static Logger logger = Logger.getLogger(SimulationSettingsController.class.getName());

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
    public SimulationSettingsController(SimulationSettingsView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        view.getBotMaxHealthPointsSlider().valueProperty().addListener((observable, oldValue, newValue) -> {
            view.getFoodEffectSlider().setMax(newValue.doubleValue());
            view.getPoisonEffectSlider().setMax(newValue.doubleValue());
        });

        view.getContinueButton().setOnMouseClicked(mouseEvent -> onContinueClick());
        view.getBackButton().setOnMouseClicked(mouseEvent -> onBackButtonClick());

        logger.log(Level.INFO, "Simulation settings page set up");
    }

    /**
     * Continue button handler react.
     */
    private void onContinueClick() {
        logger.log(Level.INFO, "Continue button clicked");
        int fieldHeight = view.getFieldHeight();
        int fieldWidth = view.getFieldWidth();
        int botCount = view.getBotCount();
        int botMaxHealth = (int) view.getBotMaxHealthPointsSlider().getValue();
        int foodEffect = (int) view.getFoodEffectSlider().getValue();
        int poisonEffect = (int) view.getPoisonEffectSlider().getValue();
        int mutationPart = (int) view.getMutationPartSlider().getValue();
        int generationMaxLivingTime = (int) view.getGenerationLivingTimeSlider().getValue();

        Field field = new Field(fieldHeight, fieldWidth, botCount, botMaxHealth, foodEffect, poisonEffect, mutationPart, generationMaxLivingTime);

        FieldSetupView fieldSetupView = new FieldSetupView(field);
        FieldSetupController mapSetupController = new FieldSetupController(fieldSetupView, stage);

        Scene scene = new Scene(fieldSetupView, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Back button handler react.
     */
    private void onBackButtonClick() {
        logger.log(Level.INFO, "Back button clicked");
        FieldSettingsView fieldSettingsView = new FieldSettingsView();
        FieldSettingsController fieldSettingsController = new FieldSettingsController(fieldSettingsView, stage);

        Scene mapSettingScene = new Scene(fieldSettingsView, 1280, 720);

        stage.setScene(mapSettingScene);
        stage.show();
    }
}
