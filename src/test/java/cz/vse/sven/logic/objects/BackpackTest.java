package cz.vse.sven.logic.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BackpackTest {
    private Backpack backpack;
    private Item item;

    @BeforeEach
    public void setUp() {
        backpack = new Backpack();
        backpack.setCapacity(1);
        item = new Item("item", "Item", true, false, false, 0);
    }

    @Test
    public void putItemIntoBackpack() {
        assertTrue(backpack.putItem(item));
        assertFalse(backpack.putItem(new Item("item2", "Item2", true, false, false, 0)));
    }

    @Test
    public void removeItemFromBackpack() {
        backpack.putItem(item);
        assertEquals(" - You handed over Item", backpack.removeItem("item"));
        assertNull(backpack.removeItem("test"));
    }

    @Test
    public void containsItem() {
        backpack.putItem(item);
        assertTrue(backpack.containsItem("item"));
        assertFalse(backpack.containsItem("test"));
    }

    @Test
    public void selectItem() {
        backpack.putItem(item);
        assertEquals(item, backpack.selectItem("item"));
        assertNull(backpack.selectItem("test"));
    }

    @Test
    public void itemList() {
        backpack.putItem(item);
        assertEquals("items in backpack: item ", backpack.itemList());
    }
}