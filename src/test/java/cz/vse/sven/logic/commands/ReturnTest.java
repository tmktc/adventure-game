package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReturnTest {

    private Backpack backpack;
    private GamePlan plan;
    private Money money;

    @BeforeEach
    public void setUp() {
        backpack = new Backpack();
        plan = new GamePlan();
        money = new Money();
    }

    @Test
    public void executeCommand() {
        ICommand cReturn = new Return(plan, backpack, money);

        // no parameter
        assertEquals("You must name the item you want to return.", cReturn.executeCommand());

        // bottle machine not in area
        assertEquals("There is not Bottle machine in here.", cReturn.executeCommand("test"));

        // item not in backpack
        plan.getCurrentArea().addItem(new Item("bottleMachine", "Bottle machine", false, false, false, 0));
        assertEquals("There is no such thing in you backpack.", cReturn.executeCommand("test"));

        // item not returnable
        backpack.putItem(new Item("nonreturnable", "Nonreturnable", false, true, false, 1));
        assertEquals("You cannot return this item.", cReturn.executeCommand("nonreturnable"));

        // all good
        backpack.putItem(new Item("returnable", "Returnable", true, false, true, 0));
        assertEquals("You returned Returnable and got 1 Euro.", cReturn.executeCommand("returnable"));


    }
}