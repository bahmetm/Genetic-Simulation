package model;

import model.cell.Bot;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenerationTest {
    Bot[] bots;

    @Before
    public void init() {
        bots = Generation.getNewGeneration(4, 50);

        byte index = 0;

        for (Bot bot : bots) {
            byte[] DNA = new byte[64];
            for (int i = 0; i < 64; i++) {
                DNA[i] = index;
            }
            bot.setDNA(DNA);
            index++;
        }
    }

    @Test
    public void getNewGenerationFromParentsWithMutationTest() {
        Bot[] newGeneration = Generation.getNewGeneration(bots, 2, 50);

        int currentMutationPart = 0;

        for (Bot newGenerationBot : newGeneration) {
            byte[] newBotDNA = newGenerationBot.getDNA();
            for (Bot bot : bots) {

                int changedGenes = 0;

                for (int i = 0; i < newBotDNA.length; i++) {
                    if (bot.getDNA()[i] != newBotDNA[i]) {
                        changedGenes++;
                    }
                }

                if (changedGenes == 1) {
                    currentMutationPart++;
                }
            }
        }
        assertEquals(8, currentMutationPart);
    }
}
