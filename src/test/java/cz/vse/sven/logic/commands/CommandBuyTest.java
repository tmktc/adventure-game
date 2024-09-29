package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void executeCommand() {
        CommandBuy commandBuy = new CommandBuy(plan, backpack, money);

        // no parameter
        assertEquals("You must name the item you want to buy.", commandBuy.executeCommand());

        // item not in area
        assertEquals("There is no such item here.", commandBuy.executeCommand("test"));

        // item not purchasable
        Item notPurchasable = new Item("notPurchasable", "NotPurchasable", true, false, true, 0);
        plan.getCurrentArea().addItem(notPurchasable);
        assertEquals("You can not buy this item.", commandBuy.executeCommand("notPurchasable"));

        // not enough money
        Item purchasable = new Item("purchasable", "Purchasable", false, true, false, 1);
        plan.getCurrentArea().addItem(purchasable);
        assertEquals("Not enough money to buy this item.", commandBuy.executeCommand("purchasable"));

        // no backpack space
        money.addMoney(1);
        backpack.setCapacity(0);
        assertEquals("Not enough space in the backpack.", commandBuy.executeCommand("purchasable"));

        // all good
        backpack.setCapacity(1);
        assertEquals("You bought Purchasable for 1 Euro.", commandBuy.executeCommand("purchasable"));
        assertFalse(backpack.selectItem("purchasable").isPurchasable());
        assertTrue(backpack.selectItem("purchasable").getCanBePickedUp());


    }
}