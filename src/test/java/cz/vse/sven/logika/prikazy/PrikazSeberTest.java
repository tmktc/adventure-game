package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazSeberTest slouží k otestování třídy PrikazSeber
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazSeberTest {

    private HerniPlan plan;
    private Batoh batoh;


    @BeforeEach
    public void setUp() {
        plan = new HerniPlan();
        batoh = new Batoh();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void provedPrikaz() {
        PrikazSeber prikazSeber = new PrikazSeber(plan, batoh);

        // žádný parametr
        assertEquals("Nezadali jste název věci, kterou chcete sebrat", prikazSeber.provedPrikaz());

        // daná věc se v prostoru nenachází
        assertEquals("Taková věc tu není", prikazSeber.provedPrikaz("test"));

        //věc není sebratelná
        plan.getAktualniProstor().addVec(new Vec("test2", "Test2",false, false, false, 0));
        assertEquals("Takovou věc nelze sebrat", prikazSeber.provedPrikaz("test2"));

        // nedostatek místa v batohu
        batoh.setKapacita(0);
        plan.getAktualniProstor().addVec(new Vec("test3", "Test3",true, false, false, 0));
        assertEquals("Nemáte dostatek místa v batohu", prikazSeber.provedPrikaz("test3"));

        // všechno v pořádku
        batoh.setKapacita(1);
        assertEquals("Sebrali jste test3", prikazSeber.provedPrikaz("test3"));
    }
}