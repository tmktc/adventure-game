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
        CommandGo prikazJdi = new CommandGo(game.getGamePlan(), progress);

        // Parametr chybí
        assertEquals("Nezadali jste místo, kam chcete jít", prikazJdi.executeCommand());

        // Není platný sousední prostor
        assertEquals("Tam se odsud jít nedá", prikazJdi.executeCommand("smetiste"));

        String spravnyText =
                "\n\nNacházíš se u jídelny, která má právě zavřeno, vedle ní postává váš kamarád Kim.\n" +
                        "\n" +
                        "východy: lidl  smetiste  trafika  domov  \n" +
                        "věci: \n" +
                        "postavy: Kim  \n";

        // Platný sousední prostor
        assertEquals(spravnyText, prikazJdi.executeCommand("jidelna"));
    }
}