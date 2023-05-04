package model.cell.edible;

import model.cell.Cell;

/**
 * Class represents edible by bot cells.
 */
public class Edible extends Cell {
    private int effect;

    /**
     * Constructor of object.
     * @param effect effect of eating cell by bot.
     */
    public Edible(int effect) {
        this.effect = effect;
    }

    /**
     * @return effect of eating cell by bot.
     */
    public int getEffect() {
        return this.effect;
    }
}
