package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoTest {

    private Game game;
    private Progress progress;

    @BeforeEach
    public void setUp() {
        game = new Game();
        progress = new Progress();
    }

    @Test
    public void executeCommand() {
        ICommand go = new Go(game.getGamePlan(), progress);

        // no parameter
        assertEquals("You must enter the area you want to go to.", go.executeCommand());

        // not valid neighboring area
        assertEquals("You can not go there from here.", go.executeCommand("junkyard"));

        String correctText =
                """


                        You are next to the Soup kitchen, which is closed for today, you see your friend Kim.

                        exits: junkyard  lidl  kiosk  home \s
                        items:\s
                        NPCs: kim \s
                        """;

        // valid neighboring area
        assertEquals(correctText, go.executeCommand("soupKitchen"));
    }
}