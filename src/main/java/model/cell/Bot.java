package model.cell;

import java.util.Arrays;
import java.util.Random;

/**
 * Class represents bot.
 * This is the main object of simulation.
 */
public class Bot extends Cell implements Cloneable {
    private byte direction;
    private int healthPoints;
    private byte[] DNA;
    private int mutationsCount;
    private byte pointerAddress;

    /**
     * Constructor of object.
     * @param healthPoints default health level of bot.
     */
    public Bot(int healthPoints) {
        Random random = new Random();
        this.direction = (byte) random.nextInt(8);
        this.healthPoints = healthPoints;
        this.DNA = new byte[64];
        for (int i = 0; i < 64; i++) {
            this.DNA[i] = (byte) random.nextInt(64);
        }
        this.mutationsCount = 0;
        this.pointerAddress = 0;
    }

    /**
     * Constructor of object.
     * @param direction default direction of bot
     * @param healthPoints default health level of bot.
     * @param DNA DNA of bot.
     * @param mutationsCount mutations count of bot.
     */
    public Bot(byte direction, int healthPoints, byte[] DNA, int mutationsCount) {
        this.direction = direction;
        this.healthPoints = healthPoints;
        this.DNA = DNA;
        this.mutationsCount = mutationsCount;
        this.pointerAddress = 0;
    }

    /**
     * Constructor to copy existing object.
     * @param botToCopy bot to make copy of.
     */
    public Bot(Bot botToCopy) {
        this(botToCopy.direction, botToCopy.healthPoints, botToCopy.DNA.clone(), botToCopy.mutationsCount);
    }

    /**
     * @return direction of bot.
     */
    public byte getDirection() {
        return this.direction;
    }

    /**
     * @param direction direction to set to bot.
     */
    public void setDirection(byte direction) {
        this.direction = direction;
    }

    /**
     * @return health level of bot.
     */
    public int getHealthPoints() {
        return this.healthPoints;
    }

    /**
     * @param healthPoints health level to set to bot.
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * @return DNA of bot.
     */
    public byte[] getDNA() {
        return this.DNA;
    }

    /**
     * @param DNA to set to bot.
     */
    public void setDNA(byte[] DNA) {
        this.DNA = DNA;
    }

    /**
     * @return mutation count of bot.
     */
    public int getMutationsCount() {
        return this.mutationsCount;
    }

    /**
     * @return pointer address of bot.
     */
    public byte getPointerAddress() {
        return this.pointerAddress;
    }

    /**
     * @param healthPoints health point to add to current bot health level.
     */
    public void addHealth(int healthPoints) {
        this.healthPoints += healthPoints;
    }

    /**
     * @param healthPoints health points to subtract from current bot health level.
     */
    public void subtractHealth(int healthPoints) {
        this.healthPoints -= healthPoints;
    }

    /**
     * @param increaseValue value to increase by current pointer address of bot.
     */
    public void increasePointerAddress(byte increaseValue) {
        byte currentPointerAddress = this.pointerAddress;
        this.pointerAddress = (byte) ((currentPointerAddress + increaseValue) % 64);
    }

    /**
     * @param angle angle to rotate bot
     */
    public void rotate(byte angle) {
        byte currentDirection = this.direction;
        this.direction = (byte) ((currentDirection + angle) % 8);
    }

    /**
     * Change random DNA value to random value
     */
    public void mutate() {
        Random random = new Random();
        int DNAAddressToChange = random.nextInt(64);
        byte DNAValueToChange = (byte) random.nextInt(64);
        DNA[DNAAddressToChange] = DNAValueToChange;
        mutationsCount++;
    }

    /**
     * @return string representation of bot DNA as table 8x8
     */
    public String getBotDNAString() {
        StringBuilder botDNA = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                botDNA.append(String.format("%02d ", DNA[i * 8 + j]));
            }
            botDNA.deleteCharAt(botDNA.length() - 1);
            botDNA.append("\n");
        }
        botDNA.deleteCharAt(botDNA.length() - 1);
        return botDNA.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bot bot = (Bot) o;

        if (direction != bot.direction) return false;
        if (healthPoints != bot.healthPoints) return false;
        if (mutationsCount != bot.mutationsCount) return false;
        if (pointerAddress != bot.pointerAddress) return false;
        return Arrays.equals(DNA, bot.DNA);
    }
}
