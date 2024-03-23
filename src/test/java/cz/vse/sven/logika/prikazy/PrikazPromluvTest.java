package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.hra.Progress;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Postava;
import cz.vse.sven.logika.objekty.Vec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PrikazPromluvTest slouží k otestování třídy PrikazPromluv
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class PrikazPromluvTest {

    private HerniPlan plan;
    private Batoh batoh;
    private Progress progress;
    private Penize penize;
    private PrikazPromluv prikazPromluv;
    private Hra hra;


    @BeforeEach
    public void setUp() {
        plan = new HerniPlan();
        batoh = new Batoh();
        penize = new Penize();
        progress = new Progress();
        hra = new Hra();
        prikazPromluv = new PrikazPromluv(plan, batoh, penize, progress, hra);
    }


    /**
     * Otestuje fungování příkazu, pokud postava, se kterou chceme mluvit, v prostoru není
     */
    @Test
    public void provedPrikaz() {
        assertEquals("Tato postava tu není.", prikazPromluv.provedPrikaz("test"));
    }


    /**
     * Otestuje správné fungování příkazu u Pepy
     */
    @Test
    public void provedPrikazPepa() {
        //podminky pro prohru
        progress.setProgress(6);
        prikazPromluv.provedPrikaz("Pepa");
        assertTrue(plan.isProhra());

        //podminky pro vyhru
        batoh.vlozVec(new Vec("PsiGranule", "Psí granule", false, false, false, 0));
        batoh.vlozVec(new Vec("Rohliky", "Rohlíky", false, false, false, 0));
        prikazPromluv.provedPrikaz("Pepa");
        assertTrue(plan.isVyhra());

        //podminky pro perfektni vyhru
        progress.setProgress(7);
        batoh.vlozVec(new Vec("Snus", "Snus", false, false, false, 0));
        prikazPromluv.provedPrikaz("Pepa");
        assertTrue(plan.isPerfektniVyhra());
    }

    /**
     * Otestuje správné fungování příkazu u Kima
     */
    @Test
    public void provedPrikazKim() {
        // podminky pro posledni dialog s Kimem
        progress.setProgress(6);
        plan.getAktualniProstor().addPostava(new Postava("Kim", "Kim"));
        batoh.vlozVec(new Vec("BezlepkovyChleba", "Bezlepkový chleba", false, false, false, 0));

        assertEquals("\n" +
                "Sven: \n" +
                "\"Ještě jednou díky za pomoc Kime, tady máš ode mě překvapení.\"\n" +
                " - Předali jste Bezlepkový chleba\n", prikazPromluv.provedPrikaz("Kim"));
        assertFalse(batoh.obsahujeVec("BezlepkovyChleba"));
        assertEquals(7, progress.getProgress());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro prohru
     */
    @Test
    public void provedPrikazPodezrelyProhra() {
        // podmínky pro neúspěšnou konfrontaci lupiče - prohru
        progress.setProgress(3);
        plan.getAktualniProstor().addPostava(new Postava("Podezrely", "Podezřelý"));
        prikazPromluv.provedPrikaz("Podezrely");

        assertTrue(plan.isProhra());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro výhru
     */
    @Test
    public void provedPrikazPodezrelyVyhra() {
        plan.getAktualniProstor().addPostava(new Postava("Podezrely", "Podezřelý"));
        progress.setProgress(4);
        prikazPromluv.provedPrikaz("Podezrely");

        assertTrue(plan.getAktualniProstor().obsahujeVec("CervenaBunda"));
        assertTrue(plan.getAktualniProstor().obsahujeVec("ZelenaCepice"));
        assertFalse(plan.getAktualniProstor().obsahujePostavu("Podezrely"));

    }

    /**
     * Otestuje správné fungování příkazu u Prodavače
     */
    @Test
    public void provedPrikazProdavac() {
        // podmínky pro získání odměny
        plan.getAktualniProstor().addPostava(new Postava("Prodavac", "Prodavač"));
        progress.setProgress(5);
        batoh.vlozVec(new Vec("CervenaBunda", "Červená bunda", false, false, false, 0));
        batoh.vlozVec(new Vec("ZelenaCepice", "Zelená Čepice", false, false, false, 0));
        prikazPromluv.provedPrikaz("Prodavac");

        assertEquals(6, progress.getProgress());
        assertEquals(4.5, penize.getPenize(), 0.0001);
        assertFalse(batoh.obsahujeVec("CervenaBunda"));
        assertFalse(batoh.obsahujeVec("ZelenaCepice"));

    }

    /**
     * Otestuje správné fungování příkazu u Zastavarnika
     */
    @Test
    public void provedPrikazZastavarnik() {
        // podmínky pro získání odměny
        plan.getAktualniProstor().addPostava(new Postava("Zastavarnik", "Zastavárník"));
        batoh.vlozVec(new Vec("StareHodiny", "Staré hodiny", false, false, false, 0));
        prikazPromluv.provedPrikaz("Zastavarnik");

        assertEquals(0.5, penize.getPenize(), 0.0001);
        assertFalse(batoh.obsahujeVec("StareHodiny"));
        assertEquals(1, progress.getProgress());
    }

}