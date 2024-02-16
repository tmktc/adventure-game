package cz.vse.sven.logika.objekty;

import cz.vse.sven.logika.hra.Penize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PenizeTest slouží k otestování třídy Penize
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PenizeTest {

    private Penize penize;

    @BeforeEach
    public void setUp() {
        penize = new Penize();
    }

    /**
     * Otestuje spránvé fungování metody
     */
    @Test
    public void pridejPenize() {

        assertEquals(0, penize.getPenize(), 0.0001);
        assertEquals(" - Dostali jste 1.0 Euro", penize.pridejPenize(1));
        assertEquals(1, penize.getPenize(), 0.0001);
    }

    /**
     * Otestuje spránvé fungování metody
     */
    @Test
    public void odectiPenize() {
        assertEquals(0, penize.getPenize(), 0.0001);
        penize.pridejPenize(20);
        penize.odectiPenize(15);
        assertEquals(5, penize.getPenize(), 0.0001);

    }
}