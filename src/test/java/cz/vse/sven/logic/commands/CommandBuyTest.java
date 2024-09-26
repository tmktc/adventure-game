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
        CommandBuy commandBuy = new CommandBuy(plan, backpack, money);

        // bez parametru
        assertEquals("You must name the item you want to buy.", commandBuy.executeCommand());

        // vec neni v prostoru
        assertEquals("There is no such item here.", commandBuy.executeCommand("test"));

        // vec neni koupitelna
        Item nekoupitelna = new Item("notPurchasable", "NotPurchasable", true, false, true, 0);
        plan.getCurrentArea().addItem(nekoupitelna);
        assertEquals("You can not buy this item.", commandBuy.executeCommand("notPurchasable"));

        // nedostatek penez
        Item koupitelna = new Item("purchasable", "Purchasable", false, true, false, 1);
        plan.getCurrentArea().addItem(koupitelna);
        assertEquals("Not enough money to buy this item.", commandBuy.executeCommand("purchasable"));

        // nedostatek mista v batohu
        money.addMoney(1);
        backpack.setCapacity(0);
        assertEquals("Not enough space in the backpack.", commandBuy.executeCommand("purchasable"));

        // všechno v pořádku
        backpack.setCapacity(1);
        assertEquals("You bought Purchasable for 1 Euro.", commandBuy.executeCommand("purchasable"));
        assertFalse(backpack.selectItem("purchasable").isPurchasable());
        assertTrue(backpack.selectItem("purchasable").getCanBePickedUp());


    }
}