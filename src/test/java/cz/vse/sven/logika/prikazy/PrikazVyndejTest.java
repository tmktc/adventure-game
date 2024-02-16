package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.HerniPlan;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testovací třída PrikazVyndejTest slouží k otestování třídy PrikazVyndej
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
class PrikazVyndejTest {

    private Batoh batoh;
    private HerniPlan plan;

    @BeforeEach
    void setUp() {
        plan = new HerniPlan();
        batoh = new Batoh();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    void provedPrikaz() {
        PrikazVyndej prikazVyndej = new PrikazVyndej(plan, batoh);

        //žádný parametr
        assertEquals("Nezadali jste název věci, kterou chcete vyndat z batohu", prikazVyndej.provedPrikaz());

        //danou věc nemáme v batohu
        assertEquals("Takovou věc u sebe nemáte", prikazVyndej.provedPrikaz("test"));

        //věc u sebe máme
        batoh.vlozVec(new Vec("vec", true, false, false, 0));
        assertEquals("vec nyní leží na zemi", prikazVyndej.provedPrikaz("vec"));
        assertTrue(plan.getAktualniProstor().obsahujeVec("vec"));
    }
}