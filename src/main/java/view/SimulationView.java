package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Field;
import view.components.FieldView;

import java.util.List;

/**
 * Simulation page.
 */
public class SimulationView extends GridPane {
    VBox lastGenerationsResults;
    private final Text healthPointsTextValue;
    private final Text mutationsCountTextValue;
    private final Text pointerAddressTextValue;
    private final Text botDNA;
    private final Button pauseButton;
    private final Button chartsButton;
    private final Button exitButton;
    private final Text currentGenerationNumber;
    private final Text currentGenerationResult;
    private final HBox currentGenerationParents;
    private final FieldView fieldView;


    /**
     * Constructor of object.
     * @param fieldView field view component.
     */
    public SimulationView(FieldView fieldView) {
        this.fieldView = fieldView;

        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10));

        // col 1 row 1
        VBox topLeftContainer = new VBox();

        HBox botInfo = new HBox();
        botInfo.setPadding(new Insets(5));
        botInfo.setMinWidth(200);

        Text healthPointsTextLabel = new Text("HP: ");
        Text mutationsCountTextLabel = new Text("MC: ");
        Text pointerAddressTextLabel = new Text("PA: ");

        healthPointsTextValue = new Text("0");
        mutationsCountTextValue = new Text("0");
        pointerAddressTextValue = new Text("0");

        healthPointsTextLabel.setFont(Font.font(null, FontWeight.BOLD, 16));
        mutationsCountTextLabel.setFont(Font.font(null, FontWeight.BOLD, 16));
        pointerAddressTextLabel.setFont(Font.font(null, FontWeight.BOLD, 16));

        healthPointsTextValue.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
        mutationsCountTextValue.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
        pointerAddressTextValue.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));

        TextFlow healthPointsText = new TextFlow(healthPointsTextLabel, healthPointsTextValue);
        TextFlow mutationsCountText = new TextFlow(mutationsCountTextLabel, mutationsCountTextValue);
        TextFlow pointerAddressText = new TextFlow(pointerAddressTextLabel, pointerAddressTextValue);

        Region region1 = new Region();
        Region region2 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);

        botInfo.getChildren().addAll(healthPointsText, region1, mutationsCountText, region2, pointerAddressText);


        StackPane botDNAContainer = new StackPane();

        botDNA = new Text();

        botDNA.setText("00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00\n" +
            "00 00 00 00 00 00 00 00");

        botDNA.setLineSpacing(2);
        botDNA.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));


        botDNAContainer.getChildren().add(botDNA);

        botDNAContainer.setMinSize(200, 200);

        botDNAContainer.setAlignment(Pos.CENTER);

        VBox controlPanel = new VBox();

        pauseButton = new Button("Start/Stop");
        chartsButton = new Button("Charts");
        chartsButton.setDisable(true);
        exitButton = new Button("Exit");

        pauseButton.setMinSize(178, 40);
        chartsButton.setMinSize(178, 40);
        exitButton.setMinSize(178, 40);

        pauseButton.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
        chartsButton.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
        exitButton.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));

        controlPanel.getChildren().addAll(pauseButton, new Separator(), chartsButton, new Separator(), exitButton);
        controlPanel.setMaxWidth(200);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setSpacing(10);
        controlPanel.setPadding(new Insets(10));

        HBox.setHgrow(botInfo, Priority.ALWAYS);
        HBox.setHgrow(botDNAContainer, Priority.ALWAYS);
        HBox.setHgrow(controlPanel, Priority.ALWAYS);

        botInfo.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        botDNAContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        controlPanel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        Region region3 = new Region();
        Region region4 = new Region();

        VBox.setVgrow(region3, Priority.ALWAYS);
        VBox.setVgrow(region4, Priority.ALWAYS);

        Label botInfoLabel = new Label("Chosen bot information", botInfo);
        botInfoLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        Label botDNALabel = new Label("Choosen bot DNA", botDNAContainer);
        botDNALabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        Label controlPanelLabel = new Label("Control panel", controlPanel);
        controlPanelLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));

        botInfoLabel.setContentDisplay(ContentDisplay.BOTTOM);
        botDNALabel.setContentDisplay(ContentDisplay.BOTTOM);
        controlPanelLabel.setContentDisplay(ContentDisplay.BOTTOM);


        topLeftContainer.getChildren().addAll(botInfoLabel, region3, botDNALabel, region4, controlPanelLabel);
        topLeftContainer.setMaxWidth(200);


        // col 2 row 1
        StackPane simulationFieldContainer = new StackPane();
        simulationFieldContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        VBox.setVgrow(simulationFieldContainer, Priority.ALWAYS);

        Label simulationFieldLabel = new Label("Simulation field", simulationFieldContainer);
        simulationFieldLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        simulationFieldLabel.setContentDisplay(ContentDisplay.BOTTOM);
        fieldView.updateSize(840, 522);
        simulationFieldContainer.getChildren().add(fieldView);


        // col 3 row 1
        ScrollPane lastGenerationsResultsContainer = new ScrollPane();

        lastGenerationsResults = new VBox();
        lastGenerationsResults.setPadding(new Insets(10));
        lastGenerationsResults.setSpacing(3);
        lastGenerationsResults.setFillWidth(true);

        lastGenerationsResults.setMinWidth(180);

        lastGenerationsResultsContainer.setMinWidth(200);
        lastGenerationsResultsContainer.prefHeightProperty().bind(topLeftContainer.heightProperty().subtract(24));

        lastGenerationsResultsContainer.setBackground(
            new Background(new BackgroundFill(Color.TRANSPARENT, null, null))
        );

        lastGenerationsResultsContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        lastGenerationsResultsContainer.setContent(lastGenerationsResults);
        lastGenerationsResultsContainer.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        lastGenerationsResultsContainer.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        Label lastGenerationsResultsLabel = new Label("Last generation results", lastGenerationsResultsContainer);
        lastGenerationsResultsLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        lastGenerationsResultsLabel.setContentDisplay(ContentDisplay.BOTTOM);


        // col 1 row 2
        StackPane currentGenerationNumberContainer = new StackPane();
        currentGenerationNumber = new Text();

        currentGenerationNumber.setText("1");
        currentGenerationNumber.setFont(Font.font(null, FontWeight.SEMI_BOLD, 20));

        currentGenerationNumberContainer.getChildren().add(currentGenerationNumber);
        currentGenerationNumberContainer.setMinSize(200, 120);
        currentGenerationNumberContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        Label currentGenerationNumberLabel = new Label("Current generation number", currentGenerationNumberContainer);
        currentGenerationNumberLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        currentGenerationNumberLabel.setContentDisplay(ContentDisplay.BOTTOM);


        // col 2 row 2
        currentGenerationParents = new HBox();

        for (int parentNumber = 0; parentNumber < Math.sqrt(fieldView.getField().getBotCount()); parentNumber++) {
            Rectangle parentView = new Rectangle(40, 40, Color.BLUE);
            Button parentButton = new Button("Parent");
            parentButton.setDisable(true);
            parentButton.setGraphic(parentView);
            parentButton.setContentDisplay(ContentDisplay.TOP);
            parentButton.setMinHeight(80);
            currentGenerationParents.getChildren().add(parentButton);
        }


        HBox.setHgrow(currentGenerationParents, Priority.ALWAYS);
        currentGenerationParents.setPadding(new Insets(10));
        currentGenerationParents.setSpacing(10);

        ScrollPane currentGenerationParentsContainer = new ScrollPane();
        currentGenerationParentsContainer.setBackground(
            new Background(new BackgroundFill(Color.TRANSPARENT, null, null))
        );
        currentGenerationParentsContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        currentGenerationParentsContainer.setContent(currentGenerationParents);
        currentGenerationParentsContainer.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        currentGenerationParentsContainer.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        Label currentGenerationParentsLabel = new Label("Current generation parents", currentGenerationParentsContainer);
        currentGenerationParentsLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        currentGenerationParentsLabel.setContentDisplay(ContentDisplay.BOTTOM);


        // col 3 row 2
        StackPane currentGenerationResultContainer = new StackPane();
        currentGenerationResult = new Text();

        currentGenerationResult.setText("0");
        currentGenerationResult.setFont(Font.font(null, FontWeight.SEMI_BOLD, 20));

        currentGenerationResultContainer.getChildren().add(currentGenerationResult);
        currentGenerationResultContainer.setMinSize(200, 120);
        currentGenerationResultContainer.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        Label currentGenerationResultLabel = new Label("Current generation result", currentGenerationResultContainer);
        currentGenerationResultLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
        currentGenerationResultLabel.setContentDisplay(ContentDisplay.BOTTOM);


        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        ColumnConstraints columnConstraints3 = new ColumnConstraints();

        RowConstraints rowConstraints1 = new RowConstraints();
        RowConstraints rowConstraints2 = new RowConstraints();

        columnConstraints2.setHgrow(Priority.ALWAYS);

        columnConstraints1.setMinWidth(200);
        columnConstraints3.setMinWidth(200);


        rowConstraints1.setVgrow(Priority.ALWAYS);

        getColumnConstraints().addAll(columnConstraints1, columnConstraints2, columnConstraints3);
        getRowConstraints().addAll(rowConstraints1, rowConstraints2);


        add(topLeftContainer, 0, 0);
        add(simulationFieldLabel, 1, 0);
        add(lastGenerationsResultsLabel, 2, 0);
        add(currentGenerationNumberLabel, 0, 1);
        add(currentGenerationParentsLabel, 1, 1);
        add(currentGenerationResultLabel, 2, 1);


        currentGenerationParentsContainer.prefWidthProperty().bind(this.widthProperty().subtract(440));

        simulationFieldContainer.prefHeightProperty().bind(this.heightProperty().subtract(198));
        simulationFieldContainer.prefWidthProperty().bind(this.widthProperty().subtract(440));
    }

    /**
     * @return pause button.
     */
    public Button getPauseButton() {
        return pauseButton;
    }

    /**
     * @return exit button.
     */
    public Button getExitButton() {
        return exitButton;
    }

    /**
     * @return field view.
     */
    public FieldView getFieldView() {
        return fieldView;
    }

    /**
     * @param healthPointsTextValue value to set to health points text element.
     */
    public void setHealthPointsTextValue(String healthPointsTextValue) {
        this.healthPointsTextValue.setText(healthPointsTextValue);
    }

    /**
     * @param mutationsCountTextValue value to set to mutations count text element.
     */
    public void setMutationsCountTextValue(String mutationsCountTextValue) {
        this.mutationsCountTextValue.setText(mutationsCountTextValue);
    }

    /**
     * @param pointerAddressTextValue value to set to pointer address text element.
     */
    public void setPointerAddressTextValue(String pointerAddressTextValue) {
        this.pointerAddressTextValue.setText(pointerAddressTextValue);
    }

    /**
     * @param botDNA value to set to bot DNA text element.
     */
    public void setBotDNA(String botDNA) {
        this.botDNA.setText(botDNA);
        this.botDNA.setLineSpacing(2);
        this.botDNA.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
    }

    /**
     * Synchronize field view to current field state.
     */
    public void updateView() {
        Field field = fieldView.getField();

        fieldView.updateFieldView(field);

        List<Integer> lastGenerationsResultsHistory = field.getGenerationResults();

        lastGenerationsResults.getChildren().clear();

        for (int i = (lastGenerationsResults.getChildren().size() + 1) / 2; i < lastGenerationsResultsHistory.size(); i++) {
            Text result = new Text(Integer.toString(lastGenerationsResultsHistory.get(i)));
            result.setFont(Font.font(null, FontWeight.NORMAL, 14));
            lastGenerationsResults.getChildren().addAll(result, new Separator());
        }

        currentGenerationResult.setText(Integer.toString(field.getCurrentGenerationLivingTime()));

        currentGenerationNumber.setText(Integer.toString(field.getCurrentGenerationNumber()));
    }
}
