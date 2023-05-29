package model.cell;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BotTest {
    Bot bot;

    @Before
    public void init() {
        bot = new Bot(50);
    }

    @Test
    public void botCopyTest() {
        Bot tempBot = new Bot(bot);
        assertEquals(bot, tempBot);
    }

    @Test
    public void botMutateTest() {
        Bot tempBot = new Bot(bot);

        tempBot.mutate();

        byte[] botDNA = bot.getDNA();
        byte[] tempBotDNA = tempBot.getDNA();

        int numberOfMutations = 0;

        for (int i = 0; i < 64; i++) {
            if (botDNA[i] != tempBotDNA[i]) {
                numberOfMutations++;
            }
        }

        assertEquals(1, numberOfMutations);
    }
}
