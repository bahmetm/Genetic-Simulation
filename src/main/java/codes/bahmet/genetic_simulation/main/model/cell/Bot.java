package codes.bahmet.genetic_simulation.main.model.cell;

/**
 * Interface Bot represents cell of type bot.
 * Bot is main object of simulation.
 */
public interface Bot extends Cell {

    /**
     * @return direction of bot
     */
    public byte getDirection();

    /**
     * @return health level of bot
     */
    public int getHealth();

    /**
     * @return count of mutations of bot
     */
    public int getMutationsCount();

    /**
     * @return DNA of bot
     */
    public byte[] getDNA();

    /**
     * @return current DNA pointer address of bot
     */
    public byte getPointerAddress();

    /**
     * @param direction new direction of bot
     */
    public void setDirection(byte direction);

    /**
     * Increase bot mutation count by 1
     */
    public void incrementMutationCount();

    /**
     * @param healthPoints points to add bot's health level
     */
    public void addHealth(int healthPoints);

    /**
     * @param healthPoints points to subtract bot's health level
     */
    public void subtractHealth(int healthPoints);

    /**
     * @param increaseValue value to add to current DNA pointer address
     */
    public void increasePointerAddress(byte increaseValue);

}
