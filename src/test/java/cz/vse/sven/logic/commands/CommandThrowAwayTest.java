package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testovací třída PrikazVyndejTest slouží k otestování třídy PrikazVyndej
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
class CommandThrowAwayTest {

    private Backpack backpack;
    private GamePlan plan;

    @BeforeEach
    void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    void executeCommand() {
        CommandThrowAway commandThrowAway = new CommandThrowAway(plan, backpack);

        //žádný parametr
        assertEquals("You must name the item you want to throw away.", commandThrowAway.executeCommand());

        //danou věc nemáme v batohu
        assertEquals("There is no such thing in you backpack.", commandThrowAway.executeCommand("test"));

        //věc u sebe máme
        backpack.putItem(new Item("item", "Item", true, false, false, 0));
        assertEquals("Item now lies on the ground.", commandThrowAway.executeCommand("item"));
        assertTrue(plan.getCurrentArea().containsItem("item"));

        //nacházíme se v lidlu
        plan.setCurrentArea(new Area("lidl", "Lidl", "test"));
        backpack.putItem(new Item("item2", "Item2", true, false, false, 0));
        assertEquals("You can not throw away items in this area.", commandThrowAway.executeCommand("item2"));

        //nacházíme se v trafice
        plan.setCurrentArea(new Area("kiosk", "test", "test"));
        assertEquals("You can not throw away items in this area.", commandThrowAway.executeCommand("item2"));

    }
}