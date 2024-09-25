package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.game.Progress;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.NPC;
import cz.vse.sven.logic.objects.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída PrikazPromluvTest slouží k otestování třídy PrikazPromluv
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandTalkTest {

    private GamePlan plan;
    private Backpack backpack;
    private Progress progress;
    private Money money;
    private CommandTalk prikazPromluv;


    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
        progress = new Progress();
        Game game = new Game();
        prikazPromluv = new CommandTalk(plan, backpack, money, progress, game);
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
        backpack.vlozVec(new Item("PsiGranule", "Psí granule", false, false, false, 0));
        backpack.vlozVec(new Item("Rohliky", "Rohlíky", false, false, false, 0));
        prikazPromluv.provedPrikaz("Pepa");
        assertTrue(plan.isVyhra());

        //podminky pro perfektni vyhru
        progress.setProgress(7);
        backpack.vlozVec(new Item("Snus", "Snus", false, false, false, 0));
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
        plan.getAktualniProstor().addPostava(new NPC("Kim", "Kim"));
        backpack.vlozVec(new Item("BezlepkovyChleba", "Bezlepkový chleba", false, false, false, 0));

        assertEquals("\n" +
                "Sven: \n" +
                "\"Ještě jednou díky za pomoc Kime, tady máš ode mě překvapení.\"\n" +
                " - Předali jste Bezlepkový chleba\n", prikazPromluv.provedPrikaz("Kim"));
        assertFalse(backpack.obsahujeVec("BezlepkovyChleba"));
        assertEquals(7, progress.getProgress());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro prohru
     */
    @Test
    public void provedPrikazPodezrelyProhra() {
        // podmínky pro neúspěšnou konfrontaci lupiče - prohru
        progress.setProgress(3);
        plan.getAktualniProstor().addPostava(new NPC("Podezrely", "Podezřelý"));
        prikazPromluv.provedPrikaz("Podezrely");

        assertTrue(plan.isProhra());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro výhru
     */
    @Test
    public void provedPrikazPodezrelyVyhra() {
        plan.getAktualniProstor().addPostava(new NPC("Podezrely", "Podezřelý"));
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
        plan.getAktualniProstor().addPostava(new NPC("Prodavac", "Prodavač"));
        progress.setProgress(5);
        backpack.vlozVec(new Item("CervenaBunda", "Červená bunda", false, false, false, 0));
        backpack.vlozVec(new Item("ZelenaCepice", "Zelená Čepice", false, false, false, 0));
        prikazPromluv.provedPrikaz("Prodavac");

        assertEquals(6, progress.getProgress());
        assertEquals(4.5, money.getPenize(), 0.0001);
        assertFalse(backpack.obsahujeVec("CervenaBunda"));
        assertFalse(backpack.obsahujeVec("ZelenaCepice"));

    }

    /**
     * Otestuje správné fungování příkazu u Zastavarnika
     */
    @Test
    public void provedPrikazZastavarnik() {
        // podmínky pro získání odměny
        plan.getAktualniProstor().addPostava(new NPC("Zastavarnik", "Zastavárník"));
        backpack.vlozVec(new Item("StareHodiny", "Staré hodiny", false, false, false, 0));
        prikazPromluv.provedPrikaz("Zastavarnik");

        assertEquals(0.5, money.getPenize(), 0.0001);
        assertFalse(backpack.obsahujeVec("StareHodiny"));
        assertEquals(1, progress.getProgress());
    }

}