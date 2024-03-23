package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazBatohTest slouží k otestování třídy PrikazBatoh
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazBatohTest {

    private Batoh batoh;


    @BeforeEach
    public void setUp() {
        batoh = new Batoh();
        Vec vec1 = new Vec("Vec1", "Věc1", true, false, false, 0);
        Vec vec2 = new Vec("Vec2", "Věc2", true, false, false, 0);
        batoh.vlozVec(vec1);
        batoh.vlozVec(vec2);
    }

    /**
     * Otestuje, zda příkaz správně vypíše obsah batohu
     */
    @Test
    public void provedPrikaz() {
        PrikazBatoh prikazBatoh = new PrikazBatoh(batoh);
        assertEquals("věci v batohu: Vec1 Vec2 ", prikazBatoh.provedPrikaz());

    }
}