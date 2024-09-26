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
    public void addMoney() {

        assertEquals(0, money.getMoney(), 0.0001);
        assertEquals(" - You received 1.0 Euro.", money.addMoney(1));
        assertEquals(1, money.getMoney(), 0.0001);
    }

    /**
     * Otestuje spránvé fungování metody
     */
    @Test
    public void subtractMoney() {
        assertEquals(0, money.getMoney(), 0.0001);
        money.addMoney(20);
        money.subtractMoney(15);
        assertEquals(5, money.getMoney(), 0.0001);

    }
}