package controller;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Field;
import model.Generation;
import model.cell.Wall;
import view.components.CellView;
import view.FieldSetupView;
import view.SimulationSettingsView;
import view.SimulationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for FieldSetupView.
 */
public class FieldSetupController {
    private FieldSetupView view;
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
    public FieldSetupController(FieldSetupView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        view.getFieldView().setOnMouseDragged(this::onMouseDragged);
        view.getFieldView().setOnMouseClicked(this::onMouseDragged);

        view.getSaveButton().setOnMouseClicked(mouseEvent -> view.getFieldView().getField().saveConfiguration("conf" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".json"));

        view.sceneProperty().addListener((obs, oldScene, newScene) -> {
            view.widthProperty().addListener((obss, oldVal, newVal) -> {
                if (Math.abs(newVal.intValue() - view.getFieldView().getCellSize() * 2) > view.getFieldView().getCellSize()) {
                    view.getFieldView().updateCellSize((int) Math.min((view.getHeight() - 120) / view.getField().getHeight(), (view.getWidth() - 20) / view.getField().getWidth()));
                }
            });

            view.heightProperty().addListener((obss, oldVal, newVal) -> {
                if (Math.abs(newVal.intValue() - view.getFieldView().getCellSize() * 2) > view.getFieldView().getCellSize()) {
                    view.getFieldView().updateCellSize((int) Math.min((view.getHeight() - 120) / view.getField().getHeight(), view.getWidth() / view.getField().getWidth()));
                }
            });
        });

        view.getContinueButton().setOnAction(actionEvent -> onContinueButtonClick());
        view.getBackButton().setOnAction(actionEvent -> onBackButtonClick());

        logger.log(Level.INFO, "Field setup page set up");
    }

    /**
     * Mouse dragging over simulation field handler react.
     * @param mouseEvent
     */
    private void onMouseDragged(MouseEvent mouseEvent) {
        CellView cellView = (CellView) mouseEvent.getPickResult().getIntersectedNode();
        Field field = view.getFieldView().getField();

        if (mouseEvent.getButton() == MouseButton.PRIMARY && cellView.getCell() == null) {
            if (field.getWallCount() < ((field.getWidth() * field.getHeight()) - (field.getWidth() * 2 + (field.getHeight() - 2) * 2)) - field.getBotCount() * 3) {
                setCellCurrentCell(cellView);
            }

        } else if (mouseEvent.getButton() == MouseButton.SECONDARY && cellView.getCell() != null) {
            eraseCell(cellView);
        }
    }

    /**
     * Set cell to current chose of type of cell.
     * @param cellView cellView to change cell.
     */
    private void setCellCurrentCell(CellView cellView) {
        switch (view.getCurrentCell()) {
            case WALL:
                view.getFieldView().getField().setCell(new Wall(), GridPane.getRowIndex(cellView), GridPane.getColumnIndex(cellView));
                cellView.setCell(new Wall());
                break;
            case FOOD:
                // TODO
                break;
            case POISON:
                // TODO
                break;
            case BOT:
                // TODO
                break;
        }
    }

    /**
     * @param cellView cellView to erase.
     */
    private void eraseCell(CellView cellView) {
        view.getFieldView().getField().deleteCell(GridPane.getRowIndex(cellView), GridPane.getColumnIndex(cellView));
        cellView.setCell(null);
    }

    /**
     * Continue button handler event.
     */
    private void onContinueButtonClick() {
        logger.log(Level.INFO, "Continue button clicked");
        view.getFieldView().getField().placeBots(Generation.getNewGeneration(view.getFieldView().getField().getBotCount(), (int) view.getFieldView().getField().getBotMaxHealth() / 2));
        view.getFieldView().getField().placeEdibleCells();

        view.getFieldView().updateFieldView();

        SimulationView simulationView = new SimulationView(view.getFieldView());
        SimulationController simulationController = new SimulationController(simulationView, stage);

        Scene simulationScene = new Scene(simulationView, 1280, 720);
        stage.setScene(simulationScene);
        stage.show();
    }

    /**
     * Back button handler event.
     */
    private void onBackButtonClick() {
        logger.log(Level.INFO, "Back button clicked");
        Field field = view.getField();

        SimulationSettingsView simulationSettingsView = new SimulationSettingsView(field.getWidth(), field.getHeight(), field.getBotCount());
        SimulationSettingsController simulationSettingsController = new SimulationSettingsController(simulationSettingsView, stage);

        Scene simulationSettingsScene = new Scene(simulationSettingsView, 1280, 720);

        stage.setScene(simulationSettingsScene);
        stage.show();
    }
}
