package cz.vse.sven.logic.objects;

import cz.vse.sven.logic.game.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PenizeTest slouží k otestování třídy Penize
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class MoneyTest {

    private Money money;

    @BeforeEach
    public void setUp() {
        money = new Money();
    }

    /**
     * Otestuje spránvé fungování metody
     */
    @Test
    public void pridejPenize() {

        assertEquals(0, money.getPenize(), 0.0001);
        assertEquals(" - Dostali jste 1.0 Euro", money.pridejPenize(1));
        assertEquals(1, money.getPenize(), 0.0001);
    }

    /**
     * Otestuje spránvé fungování metody
     */
    @Test
    public void odectiPenize() {
        assertEquals(0, money.getPenize(), 0.0001);
        money.pridejPenize(20);
        money.odectiPenize(15);
        assertEquals(5, money.getPenize(), 0.0001);

    }
}