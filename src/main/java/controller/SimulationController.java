package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.cell.Bot;
import view.components.CellView;
import view.SimulationView;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for SimulationView.
 */
public class SimulationController {
    private SimulationView view;
    private Stage stage;
    Timer timer;
    public static Logger logger = Logger.getLogger(SimulationController.class.getName());
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
    public SimulationController(SimulationView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        view.getFieldView().setOnMouseDragged(null);
        view.getFieldView().setOnMouseClicked(null);

        Thread thread = new Thread(view.getFieldView().getField());
        thread.start();

        timer = new Timer();
        timer.start();

        view.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (Math.abs(newVal.intValue() - view.getFieldView().getCellSize() * 2) > view.getFieldView().getCellSize()) {
                view.getFieldView().updateSize(view.getWidth() - 440, view.getHeight() - 198);
            }
        });

        view.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (Math.abs(newVal.intValue() - view.getFieldView().getCellSize() * 2) > view.getFieldView().getCellSize()) {
                view.getFieldView().updateSize(view.getWidth() - 440, view.getHeight() - 198);
            }
        });

        view.getFieldView().setOnMouseClicked(this::onBotMouseClick);

        view.getPauseButton().setOnMouseClicked(mouseEvent -> {
            view.getFieldView().getField().setRunning(!view.getFieldView().getField().isRunning());
        });

        view.getExitButton().setOnMouseClicked(mouseEvent -> onExitButtonClick());

        logger.log(Level.INFO, "Simulation page set up");
    }

    /**
     * Bot mouse click handler react.
     * @param mouseEvent
     */
    private void onBotMouseClick(MouseEvent mouseEvent) {
        logger.log(Level.INFO, "Mouse click on bot");
        CellView cellView = (CellView) mouseEvent.getPickResult().getIntersectedNode();
        if (cellView.getCell() instanceof Bot) {
            Bot bot = (Bot) cellView.getCell();
            view.setBotDNA(bot.getBotDNAString());
            view.setHealthPointsTextValue(Integer.toString(bot.getHealthPoints()));
            view.setMutationsCountTextValue(Integer.toString(bot.getMutationsCount()));
            view.setPointerAddressTextValue(Integer.toString(bot.getPointerAddress()));
        }
    }

    /**
     * Exit button handler react.
     */
    private void onExitButtonClick() {
        logger.log(Level.INFO, "Exit button clicked");
        stage.close();
    }

    /**
     * Class represents a timer for controlling view update.
     */
    private class Timer extends AnimationTimer {
        private long prevTime = 0;
        private double FPS = 1;
        private double UPS = 1e9 / FPS;

        @Override
        public void handle(long now) {
            if (view.getFieldView().getField().isRunning()) {
                long durationTime = now - prevTime;
                if (durationTime > UPS) {
                    view.updateView();
                    prevTime = now;
                }
            }
        }
    }
}
