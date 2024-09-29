package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThrowAwayTest {

    private Backpack backpack;
    private GamePlan plan;

    @BeforeEach
    void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
    }

    @Test
    void executeCommand() {
        ICommand throwAway = new ThrowAway(plan, backpack);

        //no parameter
        assertEquals("You must name the item you want to throw away.", throwAway.executeCommand());

        //item not in backpack
        assertEquals("There is no such thing in you backpack.", throwAway.executeCommand("test"));

        //item in backpack
        backpack.putItem(new Item("item", "Item", true, false, false, 0));
        assertEquals("Item now lies on the ground.", throwAway.executeCommand("item"));
        assertTrue(plan.getCurrentArea().containsItem("item"));

        //lidl
        plan.setCurrentArea(new Area("lidl", "Lidl", "test"));
        backpack.putItem(new Item("item2", "Item2", true, false, false, 0));
        assertEquals("You can not throw away items in this area.", throwAway.executeCommand("item2"));

        //kiosk
        plan.setCurrentArea(new Area("kiosk", "test", "test"));
        assertEquals("You can not throw away items in this area.", throwAway.executeCommand("item2"));

    }
}