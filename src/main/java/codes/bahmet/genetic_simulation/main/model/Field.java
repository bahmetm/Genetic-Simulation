package codes.bahmet.genetic_simulation.main.model;

import codes.bahmet.genetic_simulation.main.model.cell.Bot;
import codes.bahmet.genetic_simulation.main.model.cell.Cell;

/**
 * Interface Field represents field of simulation.
 * Field controls all actions of cells.
 */
public interface Field {

    /**
     * @return weight and height of field
     */
    public int[] getFieldSize();

    /**
     * Places cell to given coordinates
     * @param cell cell to place
     * @param coordinates coordinates to place cell
     */
    public void setCellCoordinates(Cell cell, int[] coordinates);

    /**
     * @param cell required to search
     * @return coordinates of given cell
     */
    public int[] getCellCoordinates(Cell cell);

    /**
     * @param cell cell to delete from field
     */
    public void deleteCell(Cell cell);

    /**
     * Update field with new generation of bots
     * @param generation generation of bots to place on field
     */
    public void updateField(Generation generation);

    /**
     * @param coordinates coordinates where located sought cell
     * @return cell on given coordinates
     */
    public Cell getCell(int[] coordinates);

    /**
     * @return all survived bots on field
     */
    public Bot[] getSurvivedBots();

    /**
     * Execute command from DNA of bot on current pointer address
     * @param bot bot which command must be executed
     */
    public void executeCommand(Bot bot);

    /**
     * Make one step
     * @param bot executor of command
     * @param direction direction, relative to bot
     */
    public void step(Bot bot, byte direction);

    /**
     * Grab food or convert poison to food
     * @param bot executor of command
     * @param direction direction, relative to bot
     */
    public void grab(Bot bot, byte direction);

    /**
     * @param bot executor of command
     * @param angle direction, relative to bot
     * @return cell located in the desired direction on the adjacent cell to the bot
     */
    public Cell look(Bot bot, byte angle);

    /**
     * @param bot executor of command
     * @param angle direction, relative to bot
     */
    public void rotate(Bot bot, byte angle);

    /**
     * Convert cell of type poison to cell food-type cell
     * @param coordinates coordinates of poison cell to convert
     */
    public void convertPoisonToFood(int[] coordinates);
}
