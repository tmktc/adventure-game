package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testovací třída PrikazKonecTest slouží k otestování třídy PrikazKonec
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandEndTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     * Otestuje správné fungování příkazu
     */
    @Test
    public void executeCommand() {
        CommandEnd commandEnd = new CommandEnd(game);

        //zadání příkazu
        assertEquals("The game was ended through a command. Thank you for playing.", commandEnd.executeCommand());

        // konec hry
        assertTrue(game.gameEnd());

    }
}