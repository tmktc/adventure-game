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
        CommandPickUp commandPickUp = new CommandPickUp(plan, backpack);

        // žádný parametr
        assertEquals("You must name the item you want to pick up.", commandPickUp.executeCommand());

        // daná věc se v prostoru nenachází
        assertEquals("There is no such item here.", commandPickUp.executeCommand("test"));

        //věc není sebratelná
        plan.getCurrentArea().addItem(new Item("test2", "Test2", false, false, false, 0));
        assertEquals("You can not pick up this item.", commandPickUp.executeCommand("test2"));

        // nedostatek místa v batohu
        backpack.setCapacity(0);
        plan.getCurrentArea().addItem(new Item("test3", "Test3", true, false, false, 0));
        assertEquals("Not enough space in the backpack.", commandPickUp.executeCommand("test3"));

        // všechno v pořádku
        backpack.setCapacity(1);
        assertEquals("You picked up Test3.", commandPickUp.executeCommand("test3"));
    }
}