package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazSeberTest slouží k otestování třídy PrikazSeber
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandPickUpTest {

    private GamePlan plan;
    private Backpack backpack;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void executeCommand() {
        CommandPickUp prikazSeber = new CommandPickUp(plan, backpack);

        // žádný parametr
        assertEquals("Nezadali jste název věci, kterou chcete sebrat", prikazSeber.executeCommand());

        // daná věc se v prostoru nenachází
        assertEquals("Taková věc tu není", prikazSeber.executeCommand("test"));

        //věc není sebratelná
        plan.getCurrentArea().addItem(new Item("test2", "Test2", false, false, false, 0));
        assertEquals("Takovou věc nelze sebrat", prikazSeber.executeCommand("test2"));

        // nedostatek místa v batohu
        backpack.setCapacity(0);
        plan.getCurrentArea().addItem(new Item("test3", "Test3", true, false, false, 0));
        assertEquals("Nemáte dostatek místa v batohu", prikazSeber.executeCommand("test3"));

        // všechno v pořádku
        backpack.setCapacity(1);
        assertEquals("Sebrali jste Test3", prikazSeber.executeCommand("test3"));
    }
}