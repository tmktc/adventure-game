package cz.vse.sven.logika.objekty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída BatohTest slouží k otestování třídy Batoh
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class BatohTest {
    private Batoh batoh;
    private Vec vec;

    @BeforeEach
    public void setUp() {
        batoh = new Batoh();
        batoh.setKapacita(1);
        vec = new Vec("Vec", "Věc",true, false, false, 0);
    }

    /**
     * Otestuje, zda je možné do batohu vložit věc a zda není možné překročit kapacitu batohu
     */
    @Test
    public void vlozVecDoBatohu() {
        assertTrue(batoh.vlozVec(vec));
        assertFalse(batoh.vlozVec(new Vec("vec2", "Věc2",true, false, false, 0)));
    }

    /**
     * Otestuje, zda je možné odstranit věc
     * Pokud věc, kterou chceme odstranit, v batohu není, otestuje, zda vrátí null
     */
    @Test
    public void odstranVecZBatohu() {
        batoh.vlozVec(vec);
        assertEquals(" - Předali jste Vec", batoh.odstranVec("Vec"));
        assertNull(batoh.odstranVec("test"));
    }

    /**
     * Otestuje, zda je možné zjistit, zda se věc v batohu nachází
     */
    @Test
    public void obsahujeVecVBatohu() {
        batoh.vlozVec(vec);
        assertTrue(batoh.obsahujeVec("Vec"));
        assertFalse(batoh.obsahujeVec("test"));
    }

    /**
     * Otestuje, zda bude vybrána správná věc
     */
    @Test
    public void vyberVecVBatohu() {
        batoh.vlozVec(vec);
        assertEquals(vec, batoh.vyberVec("Vec"));
        assertNull(batoh.vyberVec("test"));
    }

    /**
     * Otestuje, zda je správně vypsán obsah batohu
     */
    @Test
    public void nazvyVeciVBatohu() {
        batoh.vlozVec(vec);
        assertEquals("věci v batohu: Vec ", batoh.seznamVeci());
    }
}