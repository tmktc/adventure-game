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
    public void executeCommand() {
        assertEquals("Tato postava tu není.", prikazPromluv.executeCommand("test"));
    }


    /**
     * Otestuje správné fungování příkazu u Pepy
     */
    @Test
    public void executeCommandPepa() {
        //podminky pro prohru
        progress.setProgress(6);
        prikazPromluv.executeCommand("Pepa");
        assertTrue(plan.isLoss());

        //podminky pro vyhru
        backpack.putItem(new Item("PsiGranule", "Psí granule", false, false, false, 0));
        backpack.putItem(new Item("Rohliky", "Rohlíky", false, false, false, 0));
        prikazPromluv.executeCommand("Pepa");
        assertTrue(plan.isWin());

        //podminky pro perfektni vyhru
        progress.setProgress(7);
        backpack.putItem(new Item("Snus", "Snus", false, false, false, 0));
        prikazPromluv.executeCommand("Pepa");
        assertTrue(plan.isPerfectWin());
    }

    /**
     * Otestuje správné fungování příkazu u Kima
     */
    @Test
    public void executeCommandKim() {
        // podminky pro posledni dialog s Kimem
        progress.setProgress(6);
        plan.getCurrentArea().addNPC(new NPC("Kim", "Kim"));
        backpack.putItem(new Item("BezlepkovyChleba", "Bezlepkový chleba", false, false, false, 0));

        assertEquals("\n" +
                "Sven: \n" +
                "\"Ještě jednou díky za pomoc Kime, tady máš ode mě překvapení.\"\n" +
                " - Předali jste Bezlepkový chleba\n", prikazPromluv.executeCommand("Kim"));
        assertFalse(backpack.containsItem("BezlepkovyChleba"));
        assertEquals(7, progress.getProgress());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro prohru
     */
    @Test
    public void executeCommandPodezrelyProhra() {
        // podmínky pro neúspěšnou konfrontaci lupiče - prohru
        progress.setProgress(3);
        plan.getCurrentArea().addNPC(new NPC("Podezrely", "Podezřelý"));
        prikazPromluv.executeCommand("Podezrely");

        assertTrue(plan.isLoss());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro výhru
     */
    @Test
    public void executeCommandPodezrelyVyhra() {
        plan.getCurrentArea().addNPC(new NPC("Podezrely", "Podezřelý"));
        progress.setProgress(4);
        prikazPromluv.executeCommand("Podezrely");

        assertTrue(plan.getCurrentArea().containsItem("CervenaBunda"));
        assertTrue(plan.getCurrentArea().containsItem("ZelenaCepice"));
        assertFalse(plan.getCurrentArea().containsNPC("Podezrely"));

    }

    /**
     * Otestuje správné fungování příkazu u Prodavače
     */
    @Test
    public void executeCommandProdavac() {
        // podmínky pro získání odměny
        plan.getCurrentArea().addNPC(new NPC("Prodavac", "Prodavač"));
        progress.setProgress(5);
        backpack.putItem(new Item("CervenaBunda", "Červená bunda", false, false, false, 0));
        backpack.putItem(new Item("ZelenaCepice", "Zelená Čepice", false, false, false, 0));
        prikazPromluv.executeCommand("Prodavac");

        assertEquals(6, progress.getProgress());
        assertEquals(4.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("CervenaBunda"));
        assertFalse(backpack.containsItem("ZelenaCepice"));

    }

    /**
     * Otestuje správné fungování příkazu u Zastavarnika
     */
    @Test
    public void executeCommandZastavarnik() {
        // podmínky pro získání odměny
        plan.getCurrentArea().addNPC(new NPC("Zastavarnik", "Zastavárník"));
        backpack.putItem(new Item("StareHodiny", "Staré hodiny", false, false, false, 0));
        prikazPromluv.executeCommand("Zastavarnik");

        assertEquals(0.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("StareHodiny"));
        assertEquals(1, progress.getProgress());
    }

}