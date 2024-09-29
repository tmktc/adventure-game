package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandPickUpTest {

    private GamePlan plan;
    private Backpack backpack;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
    }

    @Test
    public void executeCommand() {
        CommandPickUp commandPickUp = new CommandPickUp(plan, backpack);

        // no parameter
        assertEquals("You must name the item you want to pick up.", commandPickUp.executeCommand());

        // item not in area
        assertEquals("There is no such item here.", commandPickUp.executeCommand("test"));

        //item can not be picked up
        plan.getCurrentArea().addItem(new Item("test2", "Test2", false, false, false, 0));
        assertEquals("You can not pick up this item.", commandPickUp.executeCommand("test2"));

        // not space in backpack
        backpack.setCapacity(0);
        plan.getCurrentArea().addItem(new Item("test3", "Test3", true, false, false, 0));
        assertEquals("Not enough space in the backpack.", commandPickUp.executeCommand("test3"));

        // all good
        backpack.setCapacity(1);
        assertEquals("You picked up Test3.", commandPickUp.executeCommand("test3"));
    }
}