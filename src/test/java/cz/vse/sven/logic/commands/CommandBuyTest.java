package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PrikazKupTest slouží k otestování třídy PrikazKup
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandBuyTest {

    private GamePlan plan;
    private Backpack backpack;
    private Money money;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void executeCommand() {
        CommandBuy prikazKup = new CommandBuy(plan, backpack, money);

        // bez parametru
        assertEquals("Nezadali jste název položky, kterou chcete koupit", prikazKup.executeCommand());

        // vec neni v prostoru
        assertEquals("Taková věc tu není", prikazKup.executeCommand("test"));

        // vec neni koupitelna
        Item nekoupitelna = new Item("nekoupitelna", "Nekoupitelná", true, false, true, 0);
        plan.getCurrentArea().addItem(nekoupitelna);
        assertEquals("Taková věc není koupitelná", prikazKup.executeCommand("nekoupitelna"));

        // nedostatek penez
        Item koupitelna = new Item("koupitelna", "Koupitelná", false, true, false, 1);
        plan.getCurrentArea().addItem(koupitelna);
        assertEquals("Nemáte dostatek peněz ke koupi této věci", prikazKup.executeCommand("koupitelna"));

        // nedostatek mista v batohu
        money.addMoney(1);
        backpack.setCapacity(0);
        assertEquals("Nemáte dostatek místa v batohu", prikazKup.executeCommand("koupitelna"));

        // všechno v pořádku
        backpack.setCapacity(1);
        assertEquals("Koupili jste Koupitelná za 1 Euro", prikazKup.executeCommand("koupitelna"));
        assertFalse(backpack.selectItem("koupitelna").isPurchasable());
        assertTrue(backpack.selectItem("koupitelna").getCanBePickedUp());


    }
}