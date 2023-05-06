package model.cell.edible;

/**
 * Class Poison represents cell of type poison.
 */
public class Poison extends Edible {
    /**
     * Constructor of object
     * @param effect health points to subtract to bot after eating food.
     */
    public Poison(int effect) {
        super(effect*(-1));
    }
}
