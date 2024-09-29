package cz.vse.sven.logic.objects;

import cz.vse.sven.logic.game.GamePlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AreaTest {

    private GamePlan plan;
    private Area test;
    private Item canBePickedUp;
    private Item purchasable;

    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        test = new Area("test", "test", "test");
        canBePickedUp = new Item("canBePickedUp", "CanBePickedUp", true, false, false, 0);
        purchasable = new Item("purchasable", "Purchasable", false, true, false, 1);
    }

    @Test
    public void exitsDescription() {
        assertEquals("exits: soupKitchen  ", plan.getCurrentArea().exitsDescription());
        plan.getCurrentArea().setExit(test);
        assertEquals("exits: test  soupKitchen  ", plan.getCurrentArea().exitsDescription());
    }

    @Test
    public void returnNeighboringArea() {
        assertEquals("soupKitchen", plan.getCurrentArea().returnNeighboringArea("soupKitchen").getName());
        assertNull(plan.getCurrentArea().returnNeighboringArea("test"));
    }


    @Test
    public void itemMethods() {
        //no items in area
        assertEquals("items: ", plan.getCurrentArea().itemList());

        //adding and removing items
        plan.getCurrentArea().addItem(new Item("test", "Test", true, false, false, 0));
        assertEquals("items: test  ", plan.getCurrentArea().itemList());
        plan.getCurrentArea().removeItem("test");
        assertEquals("items: ", plan.getCurrentArea().itemList());

        // checking whether the item is in the area
        assertFalse(plan.getCurrentArea().containsItem("test"));
        plan.getCurrentArea().addItem(canBePickedUp);
        assertTrue(plan.getCurrentArea().containsItem("canBePickedUp"));

        // checking whether the item is purchasable or can be picked up
        plan.getCurrentArea().addItem(purchasable);
        assertEquals(canBePickedUp, plan.getCurrentArea().canItemBePickedUp("canBePickedUp"));
        assertNull(plan.getCurrentArea().canItemBePickedUp("purchasable"));
        assertEquals(purchasable, plan.getCurrentArea().isItemPurchasable("purchasable"));
        assertNull(plan.getCurrentArea().isItemPurchasable("canBePickedUp"));
    }

    @Test
    public void NPCMethods() {
        // NPC list
        assertEquals("NPCs: peppa  ", plan.getCurrentArea().NPCList());

        // check whether the NPC is in the area
        assertTrue(plan.getCurrentArea().containsNPC("peppa"));
        assertFalse(plan.getCurrentArea().containsNPC("test"));

        // add and remove NPC
        plan.getCurrentArea().removeNPC("peppa");
        assertEquals("NPCs: ", plan.getCurrentArea().NPCList());

        plan.getCurrentArea().addNPC(new NPC("npc", "NPC"));
        assertEquals("NPCs: npc  ", plan.getCurrentArea().NPCList());
    }
}