package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Start page.
 */
public class StartView extends StackPane {
    Button customSimulationButton;
    Button presetSimulationButton;
    Button exitButton;

    CheckBox loggingCheckBox;

    /**
     * Constructor of object.
     */
    public StartView() {
        VBox mainNode = new VBox();

        Label mainLabel = new Label("Start Panel");
        mainLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        customSimulationButton = new Button("Custom Simulation");
        presetSimulationButton = new Button("Preset Simulation");
        exitButton = new Button("Exit");

        loggingCheckBox = new CheckBox("logging");
        loggingCheckBox.setSelected(true);

        customSimulationButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        presetSimulationButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        exitButton.setFont(Font.font(null, FontWeight.NORMAL, 16));

        customSimulationButton.setMaxWidth(200);
        presetSimulationButton.setMaxWidth(200);
        exitButton.setMaxWidth(200);

        mainNode.setSpacing(10);
        mainNode.setPadding(new Insets(20));
        mainNode.setAlignment(Pos.CENTER);

        mainNode.getChildren().addAll(mainLabel, customSimulationButton, presetSimulationButton, exitButton, loggingCheckBox);

        getChildren().addAll(mainNode);
    }

    /**
     * @return custom simulation button.
     */
    public Button getCustomSimulationButton() {
        return customSimulationButton;
    }

    /**
     * @return preset simulation button.
     */
    public Button getPresetSimulationButton() {
        return presetSimulationButton;
    }

    /**
     * @return exit button.
     */
    public Button getExitButton() {
        return exitButton;
    }

    /**
     * @return logging checkbox.
     */
    public CheckBox getLoggingCheckBox() {
        return loggingCheckBox;
    }
}
