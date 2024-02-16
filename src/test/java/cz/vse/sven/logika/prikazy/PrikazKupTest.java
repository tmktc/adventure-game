package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.HerniPlan;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazKupTest slouží k otestování třídy PrikazKup
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazKupTest {

    private HerniPlan plan;
    private Batoh batoh;
    private Penize penize;


    @BeforeEach
    public void setUp() {
        plan = new HerniPlan();
        batoh = new Batoh();
        penize = new Penize();
    }

    /**
     * Otestuje všechny možné případy
     */
    @Test
    public void provedPrikaz() {
        PrikazKup prikazKup = new PrikazKup(plan, batoh, penize);

        // bez parametru
        assertEquals("Nezadali jste název položky, kterou chcete koupit", prikazKup.provedPrikaz());

        // vec neni v prostoru
        assertEquals("Taková věc tu není", prikazKup.provedPrikaz("test"));

        // vec neni koupitelna
        Vec nekoupitelna = new Vec("nekoupitelna", true, false, true, 0);
        plan.getAktualniProstor().addVec(nekoupitelna);
        assertEquals("Taková věc není koupitelná", prikazKup.provedPrikaz("nekoupitelna"));

        // nedostatek penez
        Vec koupitelna = new Vec("koupitelna", false, true, false, 1);
        plan.getAktualniProstor().addVec(koupitelna);
        assertEquals("Nemáte dostatek peněz ke koupi této věci", prikazKup.provedPrikaz("koupitelna"));

        // nedostatek mista v batohu
        penize.pridejPenize(1);
        batoh.setKapacita(0);
        assertEquals("Nemáte dostatek místa v batohu", prikazKup.provedPrikaz("koupitelna"));

        // všechno v pořádku
        batoh.setKapacita(1);
        assertEquals("Koupili jste koupitelna za 1 Euro", prikazKup.provedPrikaz("koupitelna"));


    }
}