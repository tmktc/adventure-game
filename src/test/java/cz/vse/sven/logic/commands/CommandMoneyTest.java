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
    public void provedPrikaz() {
        CommandMoney prikazPenize = new CommandMoney(money);

        // nemáme žádné peníze
        assertEquals("V kapse máte 0.0 Euro", prikazPenize.provedPrikaz());

        // Přidání peněz a testování znovu
        money.pridejPenize(1.0);
        assertEquals("V kapse máte 1.0 Euro", prikazPenize.provedPrikaz());

    }
}