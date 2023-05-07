package model;

import model.cell.Bot;


/**
 * Class for generation new generation of bots.
 */
public class Generation {

    /**
     * Generates new generation of bots
     * @param botCount count of bots to generate
     * @param healthPoints default health points level of bots
     * @return array of generated bots
     */
    public static Bot[] getNewGeneration(int botCount, int healthPoints) {
        Bot[] bots = new Bot[botCount];
        for (int i = 0; i < botCount; i++) {
            bots[i] = new Bot(healthPoints);
        }
        return bots;
    }

    /**
     * Generates new generation of bots
     * @param bots parents of new generation
     * @param mutationPart part of bots which DNA changes
     * @param healthPoints default health points level of bots
     * @return array of generated bots
     */
    public static Bot[] getNewGeneration(Bot[] bots, int mutationPart, int healthPoints) {
        Bot[] newBots = new Bot[bots.length * bots.length];

        int index = 0;
        int tempMutationPart;

        for (Bot bot : bots) {
            tempMutationPart = mutationPart;
            for (int i = 0; i < bots.length; i++) {
                Bot tempBot = new Bot(bot);
                tempBot.setHealthPoints(healthPoints);
                if (tempMutationPart > 0) {
                    tempBot.mutate();
                    tempMutationPart--;
                }
                newBots[index] = tempBot;
                index++;
            }
        }
        return newBots;
    }
}
