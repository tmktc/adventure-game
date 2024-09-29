package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandEndTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void executeCommand() {
        CommandEnd commandEnd = new CommandEnd(game);

        assertEquals("The game was ended through a command. Thank you for playing.", commandEnd.executeCommand());

        assertTrue(game.isGameEnd());

    }
}