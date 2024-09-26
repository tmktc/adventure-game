package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazVymenTest slouží k otestování třídy PrikazVymen
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandReturnTest {

    private Backpack backpack;
    private GamePlan plan;
    private Money money;

    @BeforeEach
    public void setUp() {
        backpack = new Backpack();
        plan = new GamePlan();
        money = new Money();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void executeCommand() {
        CommandReturn commandReturn = new CommandReturn(plan, backpack, money);

        // bez parametru
        assertEquals("You must name the item you want to return.", commandReturn.executeCommand());

        // nenacházíme se u automatu na výměnu lahví
        assertEquals("There is not Bottle machine in here.", commandReturn.executeCommand("test"));

        // věc, kterou chceme vyměnit, u sebe nemáme
        plan.getCurrentArea().addItem(new Item("bottleMachine", "Bottle machine", false, false, false, 0));
        assertEquals("There is no such thing in you backpack.", commandReturn.executeCommand("test"));

        // věc, kterou u sebe máme a chceme vyměnit, není vyměnitelná
        backpack.putItem(new Item("nonreturnable", "Nonreturnable", false, true, false, 1));
        assertEquals("You cannot return this item.", commandReturn.executeCommand("nonreturnable"));

        // všechno správně
        backpack.putItem(new Item("returnable", "Returnable", true, false, true, 0));
        assertEquals("You returned Returnable and got 1 Euro.", commandReturn.executeCommand("returnable"));


    }
}