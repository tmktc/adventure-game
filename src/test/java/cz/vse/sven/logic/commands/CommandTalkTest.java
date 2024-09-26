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
        assertEquals("There is no such NPC in here.", prikazPromluv.executeCommand("test"));
    }


    /**
     * Otestuje správné fungování příkazu u Pepy
     */
    @Test
    public void executeCommandPepa() {
        //podminky pro prohru
        progress.setProgress(6);
        prikazPromluv.executeCommand("pepa");
        assertTrue(plan.isLoss());

        //podminky pro vyhru
        backpack.putItem(new Item("dogFood", "Dog food", false, false, false, 0));
        backpack.putItem(new Item("bagels", "Bagels", false, false, false, 0));
        prikazPromluv.executeCommand("pepa");
        assertTrue(plan.isWin());

        //podminky pro perfektni vyhru
        progress.setProgress(7);
        backpack.putItem(new Item("snus", "Snus", false, false, false, 0));
        prikazPromluv.executeCommand("pepa");
        assertTrue(plan.isPerfectWin());
    }

    /**
     * Otestuje správné fungování příkazu u Kima
     */
    @Test
    public void executeCommandKim() {
        // podminky pro posledni dialog s Kimem
        progress.setProgress(6);
        plan.getCurrentArea().addNPC(new NPC("kim", "Kim"));
        backpack.putItem(new Item("glutenFreeBread", "Gluten-free bread", false, false, false, 0));

        assertEquals("\n" +
                "Sven: \n" +
                "\"Thank you once again for your help Kim, here is a little surprise.\"\n" +
                " - You handed over Gluten-free bread\n", prikazPromluv.executeCommand("kim"));
        assertFalse(backpack.containsItem("glutenFreeBread"));
        assertEquals(7, progress.getProgress());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro prohru
     */
    @Test
    public void executeCommandPodezrelyProhra() {
        // podmínky pro neúspěšnou konfrontaci lupiče - prohru
        progress.setProgress(3);
        plan.getCurrentArea().addNPC(new NPC("suspect", "Suspect"));
        prikazPromluv.executeCommand("suspect");

        assertTrue(plan.isLoss());
    }

    /**
     * Otestuje správné fungování příkazu u Podezřelého pro výhru
     */
    @Test
    public void executeCommandPodezrelyVyhra() {
        plan.getCurrentArea().addNPC(new NPC("suspect", "Suspect"));
        progress.setProgress(4);
        prikazPromluv.executeCommand("suspect");

        assertTrue(plan.getCurrentArea().containsItem("redJacket"));
        assertTrue(plan.getCurrentArea().containsItem("greenCap"));
        assertFalse(plan.getCurrentArea().containsNPC("suspect"));

    }

    /**
     * Otestuje správné fungování příkazu u Prodavače
     */
    @Test
    public void executeCommandProdavac() {
        // podmínky pro získání odměny
        plan.getCurrentArea().addNPC(new NPC("shopAssistant", "Shop assistant"));
        progress.setProgress(5);
        backpack.putItem(new Item("redJacket", "Red jacket", false, false, false, 0));
        backpack.putItem(new Item("greenCap", "Green cap", false, false, false, 0));
        prikazPromluv.executeCommand("shopAssistant");

        assertEquals(6, progress.getProgress());
        assertEquals(4.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("redJacket"));
        assertFalse(backpack.containsItem("greenCap"));

    }

    /**
     * Otestuje správné fungování příkazu u Zastavarnika
     */
    @Test
    public void executeCommandZastavarnik() {
        // podmínky pro získání odměny
        plan.getCurrentArea().addNPC(new NPC("pawnbroker", "Pawnbroker"));
        backpack.putItem(new Item("oldClock", "Old clock", false, false, false, 0));
        prikazPromluv.executeCommand("pawnbroker");

        assertEquals(0.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("oldClock"));
        assertEquals(1, progress.getProgress());
    }

}