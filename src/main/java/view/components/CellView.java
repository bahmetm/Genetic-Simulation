package view.components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.cell.Bot;
import model.cell.Cell;
import model.cell.Wall;
import model.cell.edible.Food;
import model.cell.edible.Poison;

/**
 * CellView component.
 * Representation of given cell.
 */
public class CellView extends Rectangle {
    private Cell cell;

    public Cell getCell() {
        return cell;
    }

    /**
     * Constructor of object.
     * @param cell cell to represent.
     * @param size size of representation.
     */
    public CellView(Cell cell, double size) {
        super(size, size);

        this.cell = cell;

        setStrokeType(StrokeType.INSIDE);

        setStrokeWidth(0.15);

        setFill(getColor(cell));
        setStroke(Color.BLACK);
    }

    /**
     * @param cell sought cell
     * @return color appropriate to cell type.
     */
    private Color getColor(Cell cell) {
        if (cell instanceof Wall) {
            return Color.GRAY;
        } else if (cell instanceof Food) {
            return Color.GREEN;
        } else if (cell instanceof Poison) {
            return Color.RED;
        } else if (cell instanceof Bot) {
            return Color.BLUE;
        } else {
            return Color.WHITE;
        }
    }

    /**
     * @param cell cell to represent.
     */
    public void setCell(Cell cell) {
        this.cell = cell;
        setFill(getColor(cell));
    }
}
