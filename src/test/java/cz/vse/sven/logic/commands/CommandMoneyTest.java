package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandMoneyTest {

    private Money money;

    @BeforeEach
    public void setUp() {
        money = new Money();
    }

    @Test
    public void executeCommand() {
        CommandMoney commandMoney = new CommandMoney(money);

        // no money
        assertEquals("You have 0.0 Euro in your pocket.", commandMoney.executeCommand());

        // test again after adding money
        money.addMoney(1.0);
        assertEquals("You have 1.0 Euro in your pocket.", commandMoney.executeCommand());

    }
}