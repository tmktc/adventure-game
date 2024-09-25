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
        CommandEnd prikazKonec = new CommandEnd(game);

        //zadání příkazu
        assertEquals("Hra byla ukončena příkazem konec. Díky za zahrání.", prikazKonec.executeCommand());

        // konec hry
        assertTrue(game.gameEnd());

    }
}