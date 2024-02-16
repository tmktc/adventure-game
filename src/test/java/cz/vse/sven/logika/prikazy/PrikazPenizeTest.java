package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Penize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazPenizeTest slouží k otestování třídy PrikazPenize
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazPenizeTest {

    private Penize penize;

    @BeforeEach
    public void setUp() {
        penize = new Penize();
    }

    /**
     * Otestuje správné fungování příkazu
     */
    @Test
    public void provedPrikaz() {
        PrikazPenize prikazPenize = new PrikazPenize(penize);

        // nemáme žádné peníze
        assertEquals("V kapse máte 0.0 Euro", prikazPenize.provedPrikaz());

        // Přidání peněz a testování znovu
        penize.pridejPenize(1.0);
        assertEquals("V kapse máte 1.0 Euro", prikazPenize.provedPrikaz());

    }
}