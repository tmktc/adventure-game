package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandGoTest {

    private Game game;
    private Progress progress;

    @BeforeEach
    public void setUp() {
        game = new Game();
        progress = new Progress();
    }

    @Test
    public void executeCommand() {
        CommandGo commandGo = new CommandGo(game.getGamePlan(), progress);

        // no parameter
        assertEquals("You must enter the area you want to go to.", commandGo.executeCommand());

        // not valid neighboring area
        assertEquals("You can not go there from here.", commandGo.executeCommand("junkyard"));

        String correctText =
                "\n\nYou are next to the Soup kitchen, which is closed for today, you see your friend Kim.\n" +
                        "\n" +
                        "exits: junkyard  lidl  kiosk  home  \n" +
                        "items: \n" +
                        "NPCs: kim  \n";

        // valid neighboring area
        assertEquals(correctText, commandGo.executeCommand("soupKitchen"));
    }
}