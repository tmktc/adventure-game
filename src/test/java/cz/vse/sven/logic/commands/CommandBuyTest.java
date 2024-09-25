package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PrikazKupTest slouží k otestování třídy PrikazKup
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandBuyTest {

    private GamePlan plan;
    private Backpack backpack;
    private Money money;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void provedPrikaz() {
        CommandBuy prikazKup = new CommandBuy(plan, backpack, money);

        // bez parametru
        assertEquals("Nezadali jste název položky, kterou chcete koupit", prikazKup.provedPrikaz());

        // vec neni v prostoru
        assertEquals("Taková věc tu není", prikazKup.provedPrikaz("test"));

        // vec neni koupitelna
        Item nekoupitelna = new Item("nekoupitelna", "Nekoupitelná", true, false, true, 0);
        plan.getAktualniProstor().addVec(nekoupitelna);
        assertEquals("Taková věc není koupitelná", prikazKup.provedPrikaz("nekoupitelna"));

        // nedostatek penez
        Item koupitelna = new Item("koupitelna", "Koupitelná", false, true, false, 1);
        plan.getAktualniProstor().addVec(koupitelna);
        assertEquals("Nemáte dostatek peněz ke koupi této věci", prikazKup.provedPrikaz("koupitelna"));

        // nedostatek mista v batohu
        money.pridejPenize(1);
        backpack.setKapacita(0);
        assertEquals("Nemáte dostatek místa v batohu", prikazKup.provedPrikaz("koupitelna"));

        // všechno v pořádku
        backpack.setKapacita(1);
        assertEquals("Koupili jste Koupitelná za 1 Euro", prikazKup.provedPrikaz("koupitelna"));
        assertFalse(backpack.vyberVec("koupitelna").isKoupitelna());
        assertTrue(backpack.vyberVec("koupitelna").isSebratelna());


    }
}