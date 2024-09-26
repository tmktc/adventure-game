package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazPenizeTest slouží k otestování třídy PrikazPenize
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class CommandMoneyTest {

    private Money money;

    @BeforeEach
    public void setUp() {
        money = new Money();
    }

    /**
     * Otestuje správné fungování příkazu
     */
    @Test
    public void executeCommand() {
        CommandMoney commandMoney = new CommandMoney(money);

        // nemáme žádné peníze
        assertEquals("You have 0.0 Euro in your pocket.", commandMoney.executeCommand());

        // Přidání peněz a testování znovu
        money.addMoney(1.0);
        assertEquals("You have 1.0 Euro in your pocket.", commandMoney.executeCommand());

    }
}