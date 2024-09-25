package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazSeberTest slouží k otestování třídy PrikazSeber
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandPickUpTest {

    private GamePlan plan;
    private Backpack backpack;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void provedPrikaz() {
        CommandPickUp prikazSeber = new CommandPickUp(plan, backpack);

        // žádný parametr
        assertEquals("Nezadali jste název věci, kterou chcete sebrat", prikazSeber.provedPrikaz());

        // daná věc se v prostoru nenachází
        assertEquals("Taková věc tu není", prikazSeber.provedPrikaz("test"));

        //věc není sebratelná
        plan.getAktualniProstor().addVec(new Item("test2", "Test2", false, false, false, 0));
        assertEquals("Takovou věc nelze sebrat", prikazSeber.provedPrikaz("test2"));

        // nedostatek místa v batohu
        backpack.setKapacita(0);
        plan.getAktualniProstor().addVec(new Item("test3", "Test3", true, false, false, 0));
        assertEquals("Nemáte dostatek místa v batohu", prikazSeber.provedPrikaz("test3"));

        // všechno v pořádku
        backpack.setKapacita(1);
        assertEquals("Sebrali jste Test3", prikazSeber.provedPrikaz("test3"));
    }
}