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
        CommandReturn prikazVymen = new CommandReturn(plan, backpack, money);

        // bez parametru
        assertEquals("Nezadali jste název věci, kterou chcete vyměnit", prikazVymen.executeCommand());

        // nenacházíme se u automatu na výměnu lahví
        assertEquals("Nejsi u automatu na výměnu lahví", prikazVymen.executeCommand("test"));

        // věc, kterou chceme vyměnit, u sebe nemáme
        plan.getCurrentArea().addItem(new Item("AutomatNaLahve", "Automat na láhve", false, false, false, 0));
        assertEquals("Takovou věc u sebe nemáš", prikazVymen.executeCommand("test"));

        // věc, kterou u sebe máme a chceme vyměnit, není vyměnitelná
        backpack.putItem(new Item("nevymenitelna", "Nevyměnitelná", false, true, false, 1));
        assertEquals("Tuto věc nelze vyměnit", prikazVymen.executeCommand("nevymenitelna"));

        // všechno správně
        backpack.putItem(new Item("vymenitelna", "Vyměnitelná", true, false, true, 0));
        assertEquals("Vyměnili jste Vyměnitelná a dostali jste 1 Euro", prikazVymen.executeCommand("vymenitelna"));


    }
}