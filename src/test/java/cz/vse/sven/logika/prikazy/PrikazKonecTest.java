package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Hra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testovací třída PrikazKonecTest slouží k otestování třídy PrikazKonec
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazKonecTest {

    private Hra hra;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
    }

    /**
     * Otestuje správné fungování příkazu
     */
    @Test
    public void provedPrikaz() {
        PrikazKonec prikazKonec = new PrikazKonec(hra);

        //zadání příkazu
        assertEquals("Hra byla ukončena příkazem konec. Díky za zahrání.", prikazKonec.provedPrikaz());

        // konec hry
        assertTrue(hra.konecHry());

    }
}