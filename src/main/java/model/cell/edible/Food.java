package model.cell.edible;

/**
 * Class Food represents cell of type food.
 */
public class Food extends Edible {
    /**
     * Constructor of object
     * @param effect health points to add to bot after eating food.
     */
    public Food(int effect) {
        super(effect);
    }
}
