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
        backpack.setKapacita(1);
        item = new Item("Vec", "Věc", true, false, false, 0);
    }

    /**
     * Otestuje, zda je možné do batohu vložit věc a zda není možné překročit kapacitu batohu
     */
    @Test
    public void vlozVecDoBatohu() {
        assertTrue(backpack.vlozVec(item));
        assertFalse(backpack.vlozVec(new Item("vec2", "Věc2", true, false, false, 0)));
    }

    /**
     * Otestuje, zda je možné odstranit věc
     * Pokud věc, kterou chceme odstranit, v batohu není, otestuje, zda vrátí null
     */
    @Test
    public void odstranVecZBatohu() {
        backpack.vlozVec(item);
        assertEquals(" - Předali jste Věc", backpack.odstranVec("Vec"));
        assertNull(backpack.odstranVec("test"));
    }

    /**
     * Otestuje, zda je možné zjistit, zda se věc v batohu nachází
     */
    @Test
    public void obsahujeVecVBatohu() {
        backpack.vlozVec(item);
        assertTrue(backpack.obsahujeVec("Vec"));
        assertFalse(backpack.obsahujeVec("test"));
    }

    /**
     * Otestuje, zda bude vybrána správná věc
     */
    @Test
    public void vyberVecVBatohu() {
        backpack.vlozVec(item);
        assertEquals(item, backpack.vyberVec("Vec"));
        assertNull(backpack.vyberVec("test"));
    }

    /**
     * Otestuje, zda je správně vypsán obsah batohu
     */
    @Test
    public void nazvyVeciVBatohu() {
        backpack.vlozVec(item);
        assertEquals("věci v batohu: Vec ", backpack.seznamVeci());
    }
}