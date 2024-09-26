package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazJdiTest slouží k otestování třídy PrikazJdi
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class CommandGoTest {

    private Game game;
    private Progress progress;

    @BeforeEach
    public void setUp() {
        game = new Game();
        progress = new Progress();
    }

    /**
     * Otestuje, zda se příkaz správně vypořádá se zadaným parametrem
     */
    @Test
    public void executeCommand() {
        CommandGo commandGo = new CommandGo(game.getGamePlan(), progress);

        // Parametr chybí
        assertEquals("You must enter the area you want to go to.", commandGo.executeCommand());

        // Není platný sousední prostor
        assertEquals("You can not go there from here.", commandGo.executeCommand("junkyard"));

        String spravnyText =
                "\n\nYou are next to the Soup kitchen, which is closed for today, you see your friend Kim.\n" +
                        "\n" +
                        "exits: junkyard  lidl  kiosk  home  \n" +
                        "items: \n" +
                        "NPCs: kim  \n";

        // Platný sousední prostor
        assertEquals(spravnyText, commandGo.executeCommand("soupKitchen"));
    }
}