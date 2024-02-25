package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazVymenTest slouží k otestování třídy PrikazVymen
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazVymenTest {

    private Batoh batoh;
    private HerniPlan plan;
    private Penize penize;

    @BeforeEach
    public void setUp() {
        batoh = new Batoh();
        plan = new HerniPlan();
        penize = new Penize();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void provedPrikaz() {
        PrikazVymen prikazVymen = new PrikazVymen(plan, batoh, penize);

        // bez parametru
        assertEquals("Nezadali jste název věci, kterou chcete vyměnit", prikazVymen.provedPrikaz());

        // nenacházíme se u automatu na výměnu lahví
        assertEquals("Nejsi u automatu na výměnu lahví", prikazVymen.provedPrikaz("test"));

        // věc, kterou chceme vyměnit, u sebe nemáme
        plan.getAktualniProstor().addVec(new Vec("AutomatNaLahve", false, false, false, 0));
        assertEquals("Takovou věc u sebe nemáš", prikazVymen.provedPrikaz("test"));

        // věc, kterou u sebe máme a chceme vyměnit, není vyměnitelná
        batoh.vlozVec(new Vec("nevymenitelna", false, true, false, 1));
        assertEquals("Tuto věc nelze vyměnit", prikazVymen.provedPrikaz("nevymenitelna"));

        // všechno správně
        batoh.vlozVec(new Vec("vymenitelna", true, false, true, 0));
        assertEquals("Vyměnili jste vymenitelna a dostali jste 1 Euro", prikazVymen.provedPrikaz("vymenitelna"));


    }
}