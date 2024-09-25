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
        CommandThrowAway prikazVyndej = new CommandThrowAway(plan, backpack);

        //žádný parametr
        assertEquals("Nezadali jste název věci, kterou chcete vyndat z batohu", prikazVyndej.executeCommand());

        //danou věc nemáme v batohu
        assertEquals("Takovou věc u sebe nemáte", prikazVyndej.executeCommand("test"));

        //věc u sebe máme
        backpack.putItem(new Item("vec", "Věc", true, false, false, 0));
        assertEquals("Věc nyní leží na zemi", prikazVyndej.executeCommand("vec"));
        assertTrue(plan.getCurrentArea().containsItem("vec"));

        //nacházíme se v lidlu
        plan.setCurrentArea(new Area("lidl", "Lidl", "test"));
        backpack.putItem(new Item("vec2", "Věc2", true, false, false, 0));
        assertEquals("V tomto prostoru nelze odkládat věci", prikazVyndej.executeCommand("vec2"));

        //nacházíme se v trafice
        plan.setCurrentArea(new Area("trafika", "test", "test"));
        assertEquals("V tomto prostoru nelze odkládat věci", prikazVyndej.executeCommand("vec2"));

    }
}