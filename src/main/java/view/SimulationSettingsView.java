package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Simulation settings page.
 */
public class SimulationSettingsView extends StackPane {
    private final Slider botMaxHealthPointsSlider;
    private final Slider foodEffectSlider;
    private final Slider poisonEffectSlider;
    private final Slider mutationPartSlider;
    private final Slider generationLivingTimeSlider;

    private final Button continueButton;
    private final Button backButton;

    private final int fieldWidth;
    private final int fieldHeight;
    private final int botCount;


    /**
     * Constructor of object.
     * @param fieldWidth width of simulation field.
     * @param fieldHeight height of simulation field.
     * @param botCount count of bots on simulation field.
     */
    public SimulationSettingsView(int fieldWidth, int fieldHeight, int botCount) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.botCount = botCount;

        VBox mainContainer = new VBox();

        Label mainLabel = new Label("Simulation Settings");
        mainLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label cellsParametersLabel = new Label("Cells Parameters");
        cellsParametersLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 18));

        botMaxHealthPointsSlider = new Slider(20, 200, 100);
        botMaxHealthPointsSlider.setMajorTickUnit(20);
        botMaxHealthPointsSlider.setMinorTickCount(0);
        botMaxHealthPointsSlider.setShowTickLabels(true);
        botMaxHealthPointsSlider.setSnapToTicks(true);
        botMaxHealthPointsSlider.setPrefWidth(260);
        Label botMaxHealthPointsSliderLabel = new Label("Bot maximum health", botMaxHealthPointsSlider);
        botMaxHealthPointsSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);


        foodEffectSlider = new Slider(20, 100, 20);
        foodEffectSlider.setMajorTickUnit(20);
        foodEffectSlider.setMinorTickCount(0);
        foodEffectSlider.setShowTickLabels(true);
        foodEffectSlider.setSnapToTicks(true);
        foodEffectSlider.setPrefWidth(260);
        Label foodEffectSliderLabel = new Label("Food effect", foodEffectSlider);
        foodEffectSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);

        poisonEffectSlider = new Slider(20, 100, 100);
        poisonEffectSlider.setMajorTickUnit(20);
        poisonEffectSlider.setMinorTickCount(0);
        poisonEffectSlider.setShowTickLabels(true);
        poisonEffectSlider.setSnapToTicks(true);
        poisonEffectSlider.setPrefWidth(260);
        Label poisonEffectSliderLabel = new Label("Poison effect", poisonEffectSlider);
        poisonEffectSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);

        Label mutationPartLabel = new Label("Mutation");
        mutationPartLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 18));

        mutationPartSlider = new Slider(1, Math.sqrt(botCount) - 1, 1);
        mutationPartSlider.setMajorTickUnit(1);
        mutationPartSlider.setMinorTickCount(0);
        mutationPartSlider.setShowTickLabels(true);
        mutationPartSlider.setSnapToTicks(true);
        mutationPartSlider.setPrefWidth(260);
        Label mutationPartSliderLabel = new Label("Mutation part", mutationPartSlider);
        mutationPartSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);

        Label generationLivingTimeLabel = new Label("Generation");
        generationLivingTimeLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 18));

        generationLivingTimeSlider = new Slider(50000, 200000, 100000);
        generationLivingTimeSlider.setMajorTickUnit(50000);
        generationLivingTimeSlider.setMinorTickCount(0);
        generationLivingTimeSlider.setShowTickLabels(true);
        generationLivingTimeSlider.setSnapToTicks(true);
        generationLivingTimeSlider.setPrefWidth(260);
        Label generationLivingTimeSliderLabel = new Label("Maximum living time", generationLivingTimeSlider);
        generationLivingTimeSliderLabel.setContentDisplay(ContentDisplay.BOTTOM);


        continueButton = new Button("Continue");
        backButton = new Button("Back");

        continueButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        backButton.setFont(Font.font(null, FontWeight.NORMAL, 16));


        continueButton.prefWidthProperty().bind(mainContainer.widthProperty());
        backButton.prefWidthProperty().bind(mainContainer.widthProperty());

        mainContainer.setSpacing(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);

        mainContainer.setMaxSize(300, 500);

        mainContainer.getChildren().addAll(
            mainLabel,
            new Separator(),
            cellsParametersLabel,
            botMaxHealthPointsSliderLabel,
            foodEffectSliderLabel,
            poisonEffectSliderLabel,
            new Separator(),
            mutationPartLabel,
            mutationPartSliderLabel,
            new Separator(),
            generationLivingTimeLabel,
            generationLivingTimeSliderLabel,
            new Separator(),
            continueButton,
            backButton
        );

        mainContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT)));

        mainContainer.setFillWidth(true);
        getChildren().add(mainContainer);
    }

    /**
     * @return bot maximum health slider.
     */
    public Slider getBotMaxHealthPointsSlider() {
        return botMaxHealthPointsSlider;
    }

    /**
     * @return food effect slider.
     */
    public Slider getFoodEffectSlider() {
        return foodEffectSlider;
    }

    /**
     * @return poison effect slider.
     */
    public Slider getPoisonEffectSlider() {
        return poisonEffectSlider;
    }

    /**
     * @return mutation part slider.
     */
    public Slider getMutationPartSlider() {
        return mutationPartSlider;
    }

    /**
     * @return generation living time slider.
     */
    public Slider getGenerationLivingTimeSlider() {
        return generationLivingTimeSlider;
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

    /**
     * @return simulation field width.
     */
    public int getFieldWidth() {
        return fieldWidth;
    }

    /**
     * @return simulation field height.
     */
    public int getFieldHeight() {
        return fieldHeight;
    }

    /**
     * @return count of bots.
     */
    public int getBotCount() {
        return botCount;
    }
}
