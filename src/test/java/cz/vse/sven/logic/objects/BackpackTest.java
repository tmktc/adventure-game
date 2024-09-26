package cz.vse.sven.logic.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída BatohTest slouží k otestování třídy Batoh
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class BackpackTest {
    private Backpack backpack;
    private Item item;

    @BeforeEach
    public void setUp() {
        backpack = new Backpack();
        backpack.setCapacity(1);
        item = new Item("item", "Item", true, false, false, 0);
    }

    /**
     * Otestuje, zda je možné do batohu vložit věc a zda není možné překročit kapacitu batohu
     */
    @Test
    public void putItemDoBatohu() {
        assertTrue(backpack.putItem(item));
        assertFalse(backpack.putItem(new Item("item2", "Item2", true, false, false, 0)));
    }

    /**
     * Otestuje, zda je možné odstranit věc
     * Pokud věc, kterou chceme odstranit, v batohu není, otestuje, zda vrátí null
     */
    @Test
    public void removeItemZBatohu() {
        backpack.putItem(item);
        assertEquals(" - You handed over Item", backpack.removeItem("item"));
        assertNull(backpack.removeItem("test"));
    }

    /**
     * Otestuje, zda je možné zjistit, zda se věc v batohu nachází
     */
    @Test
    public void containsItemVBatohu() {
        backpack.putItem(item);
        assertTrue(backpack.containsItem("item"));
        assertFalse(backpack.containsItem("test"));
    }

    /**
     * Otestuje, zda bude vybrána správná věc
     */
    @Test
    public void selectItemVBatohu() {
        backpack.putItem(item);
        assertEquals(item, backpack.selectItem("item"));
        assertNull(backpack.selectItem("test"));
    }

    /**
     * Otestuje, zda je správně vypsán obsah batohu
     */
    @Test
    public void nazvyVeciVBatohu() {
        backpack.putItem(item);
        assertEquals("items in backpack: item ", backpack.itemList());
    }
}