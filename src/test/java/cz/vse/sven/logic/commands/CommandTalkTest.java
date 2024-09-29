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

public class CommandTalkTest {

    private GamePlan plan;
    private Backpack backpack;
    private Progress progress;
    private Money money;
    private CommandTalk commandTalk;

    @BeforeEach
    public void setUp() {
        plan = new GamePlan();
        backpack = new Backpack();
        money = new Money();
        progress = new Progress();
        Game game = new Game();
        commandTalk = new CommandTalk(plan, backpack, money, progress, game);
    }

    @Test
    public void executeCommand() {
        assertEquals("There is no such NPC in here.", commandTalk.executeCommand("test"));
    }

    @Test
    public void executeCommandPeppa() {
        progress.setProgress(6);
        commandTalk.executeCommand("peppa");
        assertTrue(plan.isLoss());

        backpack.putItem(new Item("dogFood", "Dog food", false, false, false, 0));
        backpack.putItem(new Item("bagels", "Bagels", false, false, false, 0));
        commandTalk.executeCommand("peppa");
        assertTrue(plan.isWin());

        progress.setProgress(7);
        backpack.putItem(new Item("snus", "Snus", false, false, false, 0));
        commandTalk.executeCommand("peppa");
        assertTrue(plan.isPerfectWin());
    }

    @Test
    public void executeCommandKim() {
        // conditions for the last dialogue with Kim
        progress.setProgress(6);
        plan.getCurrentArea().addNPC(new NPC("kim", "Kim"));
        backpack.putItem(new Item("glutenFreeBread", "Gluten-free bread", false, false, false, 0));

        assertEquals("\n" +
                "Sven: \n" +
                "\"Thank you once again for your help Kim, here is a little surprise.\"\n" +
                " - You handed over Gluten-free bread\n", commandTalk.executeCommand("kim"));
        assertFalse(backpack.containsItem("glutenFreeBread"));
        assertEquals(7, progress.getProgress());
    }

    @Test
    public void executeCommandSuspectLoss() {
        // conditions for a bad confrontation with the suspect
        progress.setProgress(3);
        plan.getCurrentArea().addNPC(new NPC("suspect", "Suspect"));
        commandTalk.executeCommand("suspect");

        assertTrue(plan.isLoss());
    }

    @Test
    public void executeCommandSuspectWin() {
        plan.getCurrentArea().addNPC(new NPC("suspect", "Suspect"));
        progress.setProgress(4);
        commandTalk.executeCommand("suspect");

        assertTrue(plan.getCurrentArea().containsItem("redJacket"));
        assertTrue(plan.getCurrentArea().containsItem("greenCap"));
        assertFalse(plan.getCurrentArea().containsNPC("suspect"));

    }

    @Test
    public void executeCommandShopAssistant() {
        // conditions for getting a reward
        plan.getCurrentArea().addNPC(new NPC("shopAssistant", "Shop assistant"));
        progress.setProgress(5);
        backpack.putItem(new Item("redJacket", "Red jacket", false, false, false, 0));
        backpack.putItem(new Item("greenCap", "Green cap", false, false, false, 0));
        commandTalk.executeCommand("shopAssistant");

        assertEquals(6, progress.getProgress());
        assertEquals(4.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("redJacket"));
        assertFalse(backpack.containsItem("greenCap"));

    }

    @Test
    public void executeCommandPawnBroker() {
        // conditions for getting a reward
        plan.getCurrentArea().addNPC(new NPC("pawnbroker", "Pawnbroker"));
        backpack.putItem(new Item("oldClock", "Old clock", false, false, false, 0));
        commandTalk.executeCommand("pawnbroker");

        assertEquals(0.5, money.getMoney(), 0.0001);
        assertFalse(backpack.containsItem("oldClock"));
        assertEquals(1, progress.getProgress());
    }

}