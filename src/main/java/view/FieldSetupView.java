package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Field;
import view.components.CellType;
import view.components.FieldView;


/**
 * Field setup page.
 */
public class FieldSetupView extends BorderPane {
    private final Button wallButton;
    private final Button botButton;
    private final Button foodButton;
    private final Button poisonButton;
    private final Button saveButton;
    private final Button continueButton;
    private final Button backButton;
    private final CellType currentCell;
    private Field field;
    private final FieldView fieldView;

    /**
     * Constructor of object.
     * @param field simulation field.
     */
    public FieldSetupView(Field field) {
        currentCell = CellType.WALL;

        this.field = field;

        VBox centerContainer = new VBox();

        fieldView = new FieldView(field, 1280, 620);

        centerContainer.getChildren().add(fieldView);

        HBox bottomContainer = new HBox();

        HBox chooseCellContainer = new HBox();
        HBox navigationContainer = new HBox();

        bottomContainer.setPrefHeight(100);
        bottomContainer.setPadding(new Insets(10, 20, 10, 20));

        wallButton = new Button("Wall");
        botButton = new Button("Bot");
        foodButton = new Button("Food");
        poisonButton = new Button("Poison");

        Rectangle wallView = new Rectangle(40, 40);
        wallView.setFill(Color.GRAY);
        wallButton.setGraphic(wallView);
        wallButton.setContentDisplay(ContentDisplay.TOP);

        Rectangle botView = new Rectangle(40, 40);
        botView.setFill(Color.BLUE);
        botButton.setGraphic(botView);
        botButton.setContentDisplay(ContentDisplay.TOP);

        Rectangle foodView = new Rectangle(40, 40);
        foodView.setFill(Color.GREEN);
        foodButton.setGraphic(foodView);
        foodButton.setContentDisplay(ContentDisplay.TOP);

        Rectangle poisonView = new Rectangle(40, 40);
        poisonView.setFill(Color.RED);
        poisonButton.setGraphic(poisonView);
        poisonButton.setContentDisplay(ContentDisplay.TOP);

        wallButton.prefHeightProperty().bind(chooseCellContainer.heightProperty());
        botButton.prefHeightProperty().bind(chooseCellContainer.heightProperty());
        foodButton.prefHeightProperty().bind(chooseCellContainer.heightProperty());
        poisonButton.prefHeightProperty().bind(chooseCellContainer.heightProperty());

        botButton.setDisable(true);
        foodButton.setDisable(true);
        poisonButton.setDisable(true);

        chooseCellContainer.getChildren().addAll(wallButton, botButton, foodButton, poisonButton);

        chooseCellContainer.setSpacing(10);

        HBox.setHgrow(chooseCellContainer, Priority.ALWAYS);

        saveButton = new Button("Save");
        continueButton = new Button("Continue");
        backButton = new Button("Back");

        saveButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        continueButton.setFont(Font.font(null, FontWeight.NORMAL, 16));
        backButton.setFont(Font.font(null, FontWeight.NORMAL, 16));

        saveButton.prefHeightProperty().bind(navigationContainer.heightProperty());
        continueButton.prefHeightProperty().bind(navigationContainer.heightProperty());
        backButton.prefHeightProperty().bind(navigationContainer.heightProperty());

        saveButton.setPrefWidth(150);
        continueButton.setPrefWidth(150);
        backButton.setPrefWidth(150);

        navigationContainer.getChildren().addAll(saveButton, backButton, continueButton);

        navigationContainer.setPadding(new Insets(15, 0, 15, 0));
        navigationContainer.setSpacing(10);

        bottomContainer.getChildren().addAll(chooseCellContainer, navigationContainer);

        bottomContainer.setSpacing(20);

        setBottom(bottomContainer);

        setCenter(centerContainer);
    }

    /**
     * @return field view component.
     */
    public FieldView getFieldView() {
        return fieldView;
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
     * @return simulation field.
     */
    public Field getField() {
        return field;
    }

    /**
     * @param field field to set.
     */
    public void setField(Field field) {
        this.field = field;
    }

    /**
     * @return current choose of cell type
     */
    public CellType getCurrentCell() {
        return currentCell;
    }

    /**
     * @return save button.
     */
    public Button getSaveButton() {
        return saveButton;
    }
}
