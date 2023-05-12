package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Field settings page.
 */
public class FieldSettingsView extends StackPane {
    private final Slider widthSlider;

    private final Slider heightSlider;

    private final ChoiceBox botCountChoiceBox;
    private final Button continueButton;
    private final Button backButton;

    /**
     * Constructor of object.
     */
    public FieldSettingsView() {
        VBox mainContainer = new VBox();

        Label mainLabel = new Label("Simulation Field Settings");
        mainLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label mapSizeLabel = new Label("Field size");
        mapSizeLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 18));

        widthSlider = new Slider(20, 100, 50);
        widthSlider.setMajorTickUnit(10);
        widthSlider.setMinorTickCount(0);
        widthSlider.setShowTickLabels(true);
        widthSlider.setSnapToTicks(true);
        Label widthSliderLabel = new Label("Width", widthSlider);
        widthSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);
        widthSlider.setPrefWidth(260);

        heightSlider = new Slider(10, 50, 30);
        heightSlider.setMajorTickUnit(10);
        heightSlider.setMinorTickCount(0);
        heightSlider.setShowTickLabels(true);
        heightSlider.setSnapToTicks(true);
        Label heightSliderLabel = new Label("Height", heightSlider);
        heightSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);
        heightSlider.setPrefWidth(260);

        Label botCountLabel = new Label("Bot count");
        botCountLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 18));

        botCountChoiceBox = new ChoiceBox();
        botCountChoiceBox.getItems().addAll(4, 9, 16, 25, 36, 49, 64, 81);
        botCountChoiceBox.setValue(64);
        botCountChoiceBox.setPrefWidth(220);
        Label botCountChoiceBoxLabel = new Label("Count", botCountChoiceBox);
        botCountChoiceBoxLabel.setContentDisplay(ContentDisplay.RIGHT);

        continueButton = new Button("Continue");
        backButton = new Button("Back");

        continueButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        backButton.setFont(Font.font(null, FontWeight.NORMAL, 16));

        continueButton.prefWidthProperty().bind(mainContainer.widthProperty());
        backButton.prefWidthProperty().bind(mainContainer.widthProperty());

        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);

        mainContainer.setMaxSize(300, 440);

        mainContainer.getChildren().addAll(
            mainLabel,
            new Separator(),
            mapSizeLabel,
            widthSliderLabel,
            heightSliderLabel,
            new Separator(),
            botCountLabel,
            botCountChoiceBoxLabel,
            new Separator(),
            continueButton,
            backButton
        );

        mainContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT)));

        getChildren().add(mainContainer);
    }

    /**
     * @return field width button.
     */
    public Slider getWidthSlider() {
        return widthSlider;
    }

    /**
     * @return field height slider.
     */
    public Slider getHeightSlider() {
        return heightSlider;
    }

    /**
     * @return bot count choice box.
     */
    public ChoiceBox getBotCountChoiceBox() {
        return botCountChoiceBox;
    }

    /**
     * @return continue button.
     */
    public Button getContinueButton() {
        return continueButton;
    }

    /**
     * @return back button.
     */
    public Button getBackButton() {
        return backButton;
    }
}
