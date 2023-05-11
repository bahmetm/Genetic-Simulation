package view.components;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Field;
import model.cell.Cell;

/**
 * Field component.
 */
public class FieldView extends GridPane {
    private Field field;
    private int cellSize;

    /**
     * Constructor of object.
     * @param field simulation field.
     * @param width width of component.
     * @param height height of component.
     */
    public FieldView(Field field, int width, int height) {
        this.field = field;

        cellSize = Math.min(height / field.getHeight(), width / field.getWidth());

        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getCell(row, col);
                CellView cellView = new CellView(cell, cellSize);

                add(cellView, col, row);
            }
        }

        setAlignment(Pos.CENTER);

        VBox.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * Update size of every cell in field.
     * @param cellSize new size of cells.
     */
    public void updateCellSize(int cellSize) {
        for (Node node : this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setWidth(cellSize);
            rectangle.setHeight(cellSize);
        }
    }

    /**
     * Update size of every cell in field.
     * @param freeWidth free width on view.
     * @param freeHeight free height on view.
     */
    public void updateSize(int freeWidth, int freeHeight) {
        cellSize = Math.min(freeHeight / field.getHeight(), freeWidth / field.getWidth());
        for (Node node : this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setWidth(cellSize);
            rectangle.setHeight(cellSize);
        }
    }

    /**
     * Update size of every cell in field.
     * @param freeWidth free width on view.
     * @param freeHeight free height on view.
     */
    public void updateSize(double freeWidth, double freeHeight) {
        cellSize = (int) Math.min((freeHeight / field.getHeight()), (freeWidth / field.getWidth()));
        for (Node node : this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setWidth(cellSize);
            rectangle.setHeight(cellSize);
        }
    }

    /**
     * Synchronize field view with current simulation field state.
     */
    public void updateFieldView() {
        updateFieldView(field);
    }

    /**
     * Synchronize field view with current simulation field state.
     * @param field field to synchronize with.
     */
    public void updateFieldView(Field field) {
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                getChildren().remove(getNodeByCoordinates(row, col, this));

                add(new CellView(field.getCell(row, col), cellSize), col, row);
            }
        }
    }

    /**
     * @param row sought row.
     * @param col sought column.
     * @param gridPane gridpane to search in.
     * @return sought cell or null when doesn't exist.
     */
    public Node getNodeByCoordinates(int row, int col, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (getRowIndex(node) == row && getColumnIndex(node) == col) {
                result = node;
                break;
            }
        }
        return result;
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
     * @return current size of every cell.
     */
    public int getCellSize() {
        return cellSize;
    }
}
