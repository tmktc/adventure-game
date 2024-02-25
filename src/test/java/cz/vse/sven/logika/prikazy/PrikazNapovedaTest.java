package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.hra.Progress;
import cz.vse.sven.logika.objekty.Batoh;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazNapovedaTest slouží k otestování třídy PrikazNapoveda
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazNapovedaTest {

    private SeznamPrikazu seznamPrikazu;
    private HerniPlan plan;
    private Progress progress;
    private Batoh batoh;
    private Penize penize;


    @BeforeEach
    public void setUp() {
        plan = new HerniPlan();
        progress = new Progress();
        batoh = new Batoh();
        penize = new Penize();

        seznamPrikazu = new SeznamPrikazu();
        seznamPrikazu.vlozPrikaz(new PrikazJdi(plan, progress));
        seznamPrikazu.vlozPrikaz(new PrikazBatoh(batoh));
        seznamPrikazu.vlozPrikaz(new PrikazKup(plan, batoh, penize));

    }

    /**
     * Otestuje, zda příkaz správně vypíše nápovědu
     */
    @Test
    public void provedPrikaz() {
        PrikazNapoveda prikazNapoveda = new PrikazNapoveda(seznamPrikazu);
        String spravnyText = "Hra má čtyři možné konce. Dva z nich jsou špatné, jeden dobrý a jeden perfektní.\n" +
                "Průběh hry se vždy posune dál díky promluvení s nějakou postavou. Dávejte pozor, co postavy říkají.\n\n" +
                "Možné příkazy: batoh jdi kup ";

        assertEquals(spravnyText, prikazNapoveda.provedPrikaz());
    }
}