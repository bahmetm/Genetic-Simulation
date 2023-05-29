package model;

import model.cell.Bot;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FieldTest {
    Field field;

    @Before
    public void init() {
        field = new Field(20, 10, 4, 90, 10, 100, 1, 50000);
    }

    @Test
    public void placeBotTest() {
        Bot[] bots = new Bot[4];

        for (int i = 0; i < 4; i++) {
            bots[i] = new Bot(50);
        }

        field.placeBots(bots);

        assertEquals(4, field.getBotCount());
    }

    @Test
    public void saveConfigTest() {
        String fileName = "testConfig.json";

        field.saveConfiguration(fileName);

        File file = new File(fileName);

        assertTrue(file.exists());
    }

    @Test
    public void loadConfigTest() {
        String fileName = "testConfig.json";

        Field fieldFromFile = Field.loadConfiguration(fileName);

        assertEquals(field, fieldFromFile);
    }

    @Test
    public void commandExecutionTest() {
        Bot bot = new Bot(50);

        bot.setDirection((byte) 0);

        byte[] newDNA = new byte[64];

        bot.setDNA(newDNA);

        field.setCell(bot, 10, 5);

        field.executeCommand(bot, 0);

        assertEquals(bot, field.getCell(9, 5));
    }
}
