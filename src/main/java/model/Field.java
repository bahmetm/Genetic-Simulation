package model;

import controller.SimulationSettingsController;
import controller.StartController;
import model.cell.Bot;
import model.cell.Cell;
import model.cell.Wall;
import model.cell.edible.Food;
import model.cell.edible.Poison;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class represents field of simulation.
 * Field controls all cells.
 */
public class Field implements Runnable {
    private final int botCount;
    private final int botMaxHealth;
    private final int foodEffect;
    private final int poisonEffect;
    private final int mutationPart;
    private final int generationMaxLivingTime;
    private final int foodCount;
    private final int poisonCount;
    private final Cell[][] fieldMap;
    private final ArrayList<Integer> generationResults;
    int currentGenerationLivingTime;
    private volatile boolean running;
    private int wallCount;
    private int currentBotCount;
    private int currentFoodCount;
    private int currentPoisonCount;
    private int currentGenerationNumber;

    public static Logger logger = Logger.getLogger(Field.class.getName());
    static {
        if (StartController.isEnableLogging()) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(Level.OFF);
        }
    }


    /**
     * Constructor of object.
     * @param height height of field.
     * @param width width of field.
     * @param botCount count of bots.
     * @param botMaxHealth maximum health level of bots.
     * @param foodEffect effect of food.
     * @param poisonEffect effect of poison.
     * @param mutationPart part of bots which DNA changes with mutation.
     * @param generationMaxLivingTime goal of surviving time of bots.
     */
    public Field(int height, int width, int botCount, int botMaxHealth, int foodEffect, int poisonEffect, int mutationPart, int generationMaxLivingTime) {
        this.fieldMap = new Cell[height][width];
        this.botCount = botCount;
        this.botMaxHealth = botMaxHealth;
        this.foodEffect = foodEffect;
        this.poisonEffect = poisonEffect;
        this.mutationPart = mutationPart;
        this.generationMaxLivingTime = generationMaxLivingTime;

        this.wallCount = 0;
        this.foodCount = botCount;
        this.poisonCount = botCount;

        this.currentBotCount = 0;
        this.currentFoodCount = 0;
        this.currentPoisonCount = 0;

        this.currentGenerationNumber = 0;
        this.currentGenerationLivingTime = 0;

        this.generationResults = new ArrayList<>();

        this.running = false;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fieldMap[i][j] = null;
            }
        }

        for (int i = 0; i < width; i++) {
            setCell(new Wall(), 0, i);
            setCell(new Wall(), fieldMap.length-1, i);
//            fieldMap[0][i] = new Wall();
//            fieldMap[fieldMap.length - 1][i] = new Wall();
        }

        for (int i = 1; i < height - 1; i++) {
            setCell(new Wall(), i, 0);
            setCell(new Wall(), i, fieldMap[0].length - 1);
//            fieldMap[i][0] = new Wall();
//            fieldMap[i][fieldMap[0].length - 1] = new Wall();
        }
    }

    /**
     * @return default counts of bots.
     */
    public int getBotCount() {
        return botCount;
    }

    /**
     * @return count of walls.
     */
    public int getWallCount() {
        return wallCount;
    }

    /**
     * @return width of field.
     */
    public int getWidth() {
        return this.fieldMap[0].length;
    }

    /**
     * @return height of field.
     */
    public int getHeight() {
        return this.fieldMap.length;
    }

    /**
     * @param cell sought cell.
     * @return array represents coordinates of cell (row, column).
     */
    public int[] getCellCoordinates(Cell cell) {
        for (int row = 0; row < this.fieldMap.length; row++) {
            for (int col = 0; col < this.fieldMap[0].length; col++) {
                Cell tempCell = this.fieldMap[row][col];
                if (tempCell != null) {
                    if (this.fieldMap[row][col].equals(cell)) {
                        return new int[]{row, col};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Place bot on field in random spots.
     * @param bots array of bots to place.
     */
    public void placeBots(Bot[] bots) {
        Random random = new Random();

        int minWidth = 1;
        int maxWidth = getWidth() - 1;

        int minHeight = 1;
        int maxHeight = getHeight() - 1;

        int width, height;

        while (currentBotCount < botCount) {
            width = random.nextInt(random.nextInt(maxWidth - minWidth + 1) + minWidth);
            height = random.nextInt(random.nextInt(maxHeight - minHeight + 1) + minHeight);
            if (getCell(height, width) == null) {
                setCell(bots[currentBotCount], height, width);
            }
        }
    }

    /**
     * Places cell on field on given coordinates.
     * @param cell cell to place.
     * @param row row to place.
     * @param col column to place.
     */
    public void setCell(Cell cell, int row, int col) {
        this.fieldMap[row][col] = cell;
        if (cell instanceof Wall) {
            wallCount++;
        } else if (cell instanceof Bot) {
            currentBotCount++;
        } else if (cell instanceof Food) {
            currentFoodCount++;
        } else if (cell instanceof Poison) {
            currentPoisonCount++;
        }
    }

    /**
     * Delete cell on given coordinates.
     * @param row row do delete.
     * @param col column to delete.
     */
    public void deleteCell(int row, int col) {
        if (getCell(row, col) instanceof Wall) {
            wallCount--;
        }
        if (getCell(row, col) instanceof Bot) {
            currentBotCount--;
        }
        if (getCell(row, col) instanceof Food) {
            currentFoodCount--;
        }
        if (getCell(row, col) instanceof Poison) {
            currentPoisonCount--;
        }
        fieldMap[row][col] = null;
    }

    /**
     * Delete given cell.
     * @param cell cell to delete.
     */
    public void deleteCell(Cell cell) {
        if (cell instanceof Bot) {
            currentBotCount--;
        }
        if (cell instanceof Food) {
            currentFoodCount--;
        }
        if (cell instanceof Poison) {
            currentPoisonCount--;
        }
        int[] cellCoordinates = getCellCoordinates(cell);
        this.fieldMap[cellCoordinates[0]][cellCoordinates[1]] = null;
    }

    /**
     * Place edible cells (food, poison) in random spots.
     */
    public void placeEdibleCells() {
        Random random = new Random();

        int minWidth = 1;
        int maxWidth = getWidth() - 1;

        int minHeight = 1;
        int maxHeight = getHeight() - 1;

        int width, height;

        while (currentFoodCount < foodCount) {
            width = random.nextInt(random.nextInt(maxWidth - minWidth + 1) + minWidth);
            height = random.nextInt(random.nextInt(maxHeight - minHeight + 1) + minHeight);
            if (getCell(height, width) == null) {
                setCell(new Food(foodEffect), height, width);
            }
        }

        while (currentPoisonCount < poisonCount) {
            width = (int) (Math.random() * (maxWidth - minWidth) + minWidth);
            height = (int) (Math.random() * (maxHeight - minHeight) + minHeight);
            if (getCell(height, width) == null) {
                setCell(new Poison(poisonEffect), height, width);
            }
        }
    }

    /**
     * @return maximum bot health.
     */
    public int getBotMaxHealth() {
        return botMaxHealth;
    }

    /**
     * Updates field with new generation of bots.
     */
    public void updateFieldWithNewGeneration() {
        logger.log(Level.INFO, "Updating field with new generation of bots");

        Bot[] survivedBots = getSurvivedBots();

        Bot[] newGeneration = Generation.getNewGeneration(survivedBots, mutationPart, botMaxHealth / 2);

        for (Bot survivedBot : survivedBots) {
            deleteCell(survivedBot);
        }

        placeBots(newGeneration);

        if (currentGenerationLivingTime > 300) {
            System.out.println(currentGenerationLivingTime);
        }

        currentGenerationLivingTime = 0;
    }

    /**
     * @param height sought row.
     * @param width sought column.
     * @return sought cell or null.
     */
    public Cell getCell(int height, int width) {
        return fieldMap[height][width];
    }

    /**
     * @return all alive bots on field.
     */
    public Bot[] getSurvivedBots() {
        Bot[] survivedBots = new Bot[currentBotCount];

        int index = 0;
        for (Cell[] cells : this.fieldMap) {
            for (int j = 0; j < this.fieldMap[0].length; j++) {
                if (cells[j] instanceof Bot) {
                    survivedBots[index] = (Bot) cells[j];
                    index++;
                }
            }
        }
        return survivedBots;
    }


    /**
     * Executes command on current pointer address of bot.
     * @param bot bot to execute command.
     * @param commandNumber count of command executions in row.
     */
    public void executeCommand(Bot bot, int commandNumber) {
        if (bot.getHealthPoints() <= 0) {
            deleteCell(bot);
            return;
        }

        if (commandNumber > 9) {
            return;
        }

        bot.subtractHealth(10);

        byte[] botDNA = bot.getDNA();
        int botPointerAddress = bot.getPointerAddress();

        byte command = botDNA[botPointerAddress];

        if (0 <= command && command <= 7) {
            bot.increasePointerAddress(step(bot, command));
        } else if (8 <= command && command <= 15) {
            bot.increasePointerAddress((byte) (grab(bot, (byte) (command - 8)) + 1));
        } else if (16 <= command && command <= 23) {
            bot.increasePointerAddress((byte) (look(bot, (byte) (command - 16)) + 1));
            executeCommand(bot, ++commandNumber);
        } else if (24 <= command && command <= 31) {
            bot.increasePointerAddress((byte) 1);
            bot.rotate((byte) (command - 24));
            executeCommand(bot, ++commandNumber);
        } else {
            bot.increasePointerAddress(command);
            executeCommand(bot, ++commandNumber);
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes step command.
     * @param bot bot to step.
     * @param direction direction to step.
     * @return pointer increase value.
     */
    public byte step(Bot bot, byte direction) {
        int[] botCoordinates = getCellCoordinates(bot);
        int[] targetCoordinates = getCoordinatesNearBot(bot, direction);

        byte pointerAddressEffect = 0;

        Cell targetCell = getCell(targetCoordinates[0], targetCoordinates[1]);

        if (targetCell instanceof Poison) {
            deleteCell(targetCoordinates[0], targetCoordinates[1]);
            deleteCell(botCoordinates[0], botCoordinates[1]);
            setCell(bot, targetCoordinates[0], targetCoordinates[1]);
            bot.subtractHealth(((Poison) targetCell).getEffect());
        } else if (targetCell instanceof Wall) {
            pointerAddressEffect = 1;
        } else if (targetCell instanceof Bot) {
            pointerAddressEffect = 2;
        } else if (targetCell instanceof Food) {
            deleteCell(targetCoordinates[0], targetCoordinates[1]);
            deleteCell(botCoordinates[0], botCoordinates[1]);
            setCell(bot, targetCoordinates[0], targetCoordinates[1]);
            bot.addHealth(((Food) targetCell).getEffect());
            pointerAddressEffect = 3;
        } else if (targetCell == null) {
            deleteCell(botCoordinates[0], botCoordinates[1]);
            setCell(bot, targetCoordinates[0], targetCoordinates[1]);
            pointerAddressEffect = 4;
        }

        return pointerAddressEffect;
    }

    /**
     * Executes grab command.
     * @param bot bot to grab.
     * @param direction direction to grab.
     * @return pointer increase value.
     */
    public byte grab(Bot bot, byte direction) {
        int[] botCoordinates = getCellCoordinates(bot);
        int[] targetCoordinates = getCoordinatesNearBot(bot, direction);

        byte pointerAddressEffect = 0;

        Cell targetCell = getCell(targetCoordinates[0], targetCoordinates[1]);

        if (targetCell instanceof Poison) {
            deleteCell(targetCoordinates[0], targetCoordinates[1]);
            setCell(new Food(foodEffect), targetCoordinates[0], targetCoordinates[1]);
        } else if (targetCell instanceof Wall) {
            pointerAddressEffect = 1;
        } else if (targetCell instanceof Bot) {
            pointerAddressEffect = 2;
        } else if (targetCell instanceof Food) {
            deleteCell(botCoordinates[0], botCoordinates[1]);
            bot.addHealth(((Food) targetCell).getEffect());
            pointerAddressEffect = 3;
        } else if (targetCell == null) {
            pointerAddressEffect = 4;
        }

        return pointerAddressEffect;
    }


    /**
     * Executes look command.
     * @param bot bot to look.
     * @param direction direction to look.
     * @return pointer increase value.
     */
    public byte look(Bot bot, byte direction) {
        int[] targetCoordinates = getCoordinatesNearBot(bot, direction);

        byte pointerAddressEffect = 0;

        Cell targetCell = getCell(targetCoordinates[0], targetCoordinates[1]);

        if (targetCell instanceof Poison) {
        } else if (targetCell instanceof Wall) {
            pointerAddressEffect = 1;
        } else if (targetCell instanceof Bot) {
            pointerAddressEffect = 2;
        } else if (targetCell instanceof Food) {
            pointerAddressEffect = 3;
        } else if (targetCell == null) {
            pointerAddressEffect = 4;
        }

        return pointerAddressEffect;
    }


    /**
     * Get coordinates of nearest bot cell depending on bot direction and target direction.
     * @param bot bot.
     * @param direction direction of searching cell.
     * @return sought cell.
     */
    private int[] getCoordinatesNearBot(Bot bot, byte direction) {
        int[] botCoordinates = getCellCoordinates(bot);
        int targetHeight = botCoordinates[0];
        int targetWidth = botCoordinates[1];

        byte targetDirection = (byte) ((bot.getDirection() + direction) % 8);

        switch (targetDirection) {
            case 0:
                targetHeight--;
                break;
            case 1:
                targetHeight--;
                targetWidth++;
                break;
            case 2:
                targetWidth++;
                break;
            case 3:
                targetHeight++;
                targetWidth++;
                break;
            case 4:
                targetHeight++;
                break;
            case 5:
                targetHeight++;
                targetWidth--;
                break;
            case 6:
                targetWidth--;
                break;
            case 7:
                targetHeight--;
                targetWidth--;
                break;
            default:
                break;
        }
        return new int[]{targetHeight, targetWidth};
    }

    /**
     * Make next step of simulation.
     * @throws InterruptedException
     */
    public void nextStep() throws InterruptedException {
        Bot[] bots = getSurvivedBots();

        for (Bot bot : bots) {
            executeCommand(bot, 0);
            if (currentBotCount == Math.sqrt(botCount)) {
                if (generationResults.size() > 10) {
                    generationResults.remove(0);
                }
                generationResults.add(currentGenerationLivingTime);
                updateFieldWithNewGeneration();
                currentGenerationNumber++;
                return;
            }
        }
        currentGenerationLivingTime++;

        placeEdibleCells();
    }

    /**
     * @return current generation living time
     */
    public int getCurrentGenerationLivingTime() {
        return currentGenerationLivingTime;
    }

    /**
     * Main simulation behavior.
     * Make next step of simulation, until the generation reaches goal of surviving.
     */
    @Override
    public void run() {
        while (currentGenerationLivingTime < generationMaxLivingTime) {
            if (running) {
                try {
                    nextStep();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @return result of surviving last generations.
     */
    public ArrayList<Integer> getGenerationResults() {
        return generationResults;
    }

    /**
     * @return param of running simulation.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running run of pause simulation.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return number of current generation.
     */
    public int getCurrentGenerationNumber() {
        return currentGenerationNumber;
    }


    /**
     * Saves configuration of current simulation.
     * Not including coordinated of edible cells and bots.
     * @param filename file to save configuration to
     */
    public void saveConfiguration(String filename) {
        logger.log(Level.INFO, "Saving configuration");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(String.valueOf(this.fieldMap.length));
            writer.newLine();
            writer.write(String.valueOf(this.fieldMap[0].length));
            writer.newLine();
            writer.write(String.valueOf(this.botCount));
            writer.newLine();
            writer.write(String.valueOf(this.botMaxHealth));
            writer.newLine();
            writer.write(String.valueOf(this.foodEffect));
            writer.newLine();
            writer.write(String.valueOf(this.poisonEffect));
            writer.newLine();
            writer.write(String.valueOf(this.mutationPart));
            writer.newLine();
            writer.write(String.valueOf(this.generationMaxLivingTime));
            writer.newLine();

            for (int row = 0; row < fieldMap.length; row++) {
                for (int col = 0; col < fieldMap[0].length; col++) {
                    Cell cell = fieldMap[row][col];
                    char character;
                    if (cell instanceof Wall) {
                        character = 'W';
                    } else if (cell instanceof Bot) {
                        character = 'B';
                    } else if (cell instanceof Food) {
                        character = 'F';
                    } else if (cell instanceof Poison) {
                        character = 'P';
                    } else {
                        character = 'N';
                    }
                    writer.write(character);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Saving configuration failed", e);
        }
    }

    /**
     * Get field from configuration file.
     * @param filePath absolute path to configuration file.
     * @return simulation field.
     */
    public static Field loadConfiguration(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));

            Field field = new Field(Integer.parseInt(lines.get(0)), Integer.parseInt(lines.get(1)), Integer.parseInt(lines.get(2)), Integer.parseInt(lines.get(3)), Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)), Integer.parseInt(lines.get(6)), Integer.parseInt(lines.get(7)));

            char[] map = lines.get(8).toCharArray();

            for (int i = 0; i < map.length; i++) {
                char cellType = map[i];
                switch (cellType) {
                    case 'W':
                        field.setCell(new Wall(), i / Integer.parseInt(lines.get(1)), i % Integer.parseInt(lines.get(1)));
                        break;
                }

            }

            logger.log(Level.INFO, "Configuraton file read success");

            return field;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Configuration file read error", e);
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (botCount != field.botCount) return false;
        if (botMaxHealth != field.botMaxHealth) return false;
        if (foodEffect != field.foodEffect) return false;
        if (poisonEffect != field.poisonEffect) return false;
        if (mutationPart != field.mutationPart) return false;
        if (generationMaxLivingTime != field.generationMaxLivingTime) return false;
        if (foodCount != field.foodCount) return false;
        if (poisonCount != field.poisonCount) return false;
        if (currentGenerationLivingTime != field.currentGenerationLivingTime) return false;
        if (running != field.running) return false;
        if (wallCount*2 != field.wallCount) return false;
        if (currentBotCount != field.currentBotCount) return false;
        if (currentFoodCount != field.currentFoodCount) return false;
        if (currentPoisonCount != field.currentPoisonCount) return false;
        if (currentGenerationNumber != field.currentGenerationNumber) return false;
        if (fieldMap.length != field.fieldMap.length) return false;
        for (int i = 0; i < fieldMap.length; i++) {
            if (fieldMap[i].getClass() != field.fieldMap[i].getClass()) {
                return false;
            }
        }
        return generationResults.equals(field.generationResults);
    }
}
