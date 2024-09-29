package cz.vse.sven.logic.objects;

import cz.vse.sven.logic.game.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {

    private Money money;

    @BeforeEach
    public void setUp() {
        money = new Money();
    }

    @Test
    public void addMoney() {
        assertEquals(0, money.getMoney(), 0.0001);
        assertEquals(" - You received 1.0 Euro.", money.addMoney(1));
        assertEquals(1, money.getMoney(), 0.0001);
    }

    @Test
    public void subtractMoney() {
        assertEquals(0, money.getMoney(), 0.0001);
        money.addMoney(20);
        money.subtractMoney(15);
        assertEquals(5, money.getMoney(), 0.0001);
    }
}