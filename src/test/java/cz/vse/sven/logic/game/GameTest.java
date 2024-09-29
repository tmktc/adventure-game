package cz.vse.sven.logic.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cz.vse.sven.logic.commands.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void gameplayLoss1() {
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(PickUp.NAME, "oldClock");
        game.processCommand(Go.NAME, "pawnshop");
        game.processCommand(Talk.NAME, "pawnbroker");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(Talk.NAME, "suspect");
        assertTrue(game.getGamePlan().isLoss());
    }

    @Test
    public void gameplayLoss2() {
        // did not manage to buy food in time
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(PickUp.NAME, "oldClock");
        game.processCommand(Go.NAME, "pawnshop");
        game.processCommand(Talk.NAME, "pawnbroker");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Talk.NAME, "kim");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(PickUp.NAME, "redJacket");
        game.processCommand(PickUp.NAME, "greenCap");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "home");
        game.processCommand(Talk.NAME, "peppa");
        assertTrue(game.getGamePlan().isLoss());
    }

    @Test
    public void gameplayWin() {
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(PickUp.NAME, "oldClock");
        game.processCommand(Go.NAME, "pawnshop");
        game.processCommand(Talk.NAME, "pawnbroker");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Talk.NAME, "kim");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(PickUp.NAME, "redJacket");
        game.processCommand(PickUp.NAME, "greenCap");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "lidl");
        game.processCommand(Buy.NAME, "bagels");
        game.processCommand(Buy.NAME, "dogFood");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "home");
        game.processCommand(Talk.NAME, "peppa");
        assertTrue(game.getGamePlan().isWin());
    }

    @Test
    public void gameplayPerfectWin() {
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(PickUp.NAME, "oldClock");
        game.processCommand(PickUp.NAME, "svijanyBottle");
        game.processCommand(PickUp.NAME, "pilsnerBottle");
        game.processCommand(PickUp.NAME, "branikBottle");
        game.processCommand(Go.NAME, "pawnshop");
        game.processCommand(Talk.NAME, "pawnbroker");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Talk.NAME, "kim");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "jobCenter");
        game.processCommand(Talk.NAME, "suspect");
        game.processCommand(PickUp.NAME, "redJacket");
        game.processCommand(PickUp.NAME, "greenCap");
        game.processCommand(Go.NAME, "thriftShop");
        game.processCommand(Talk.NAME, "shopAssistant");
        game.processCommand(Go.NAME, "junkyard");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Go.NAME, "lidl");
        game.processCommand(Return.NAME, "svijanyBottle");
        game.processCommand(Return.NAME, "pilsnerBottle");
        game.processCommand(Return.NAME, "branikBottle");
        game.processCommand(Buy.NAME, "bagels");
        game.processCommand(Buy.NAME, "dogFood");
        game.processCommand(Buy.NAME, "glutenFreeBread");
        game.processCommand(Go.NAME, "kiosk");
        game.processCommand(Buy.NAME, "snus");
        game.processCommand(Go.NAME, "soupKitchen");
        game.processCommand(Talk.NAME, "kim");
        game.processCommand(Go.NAME, "home");
        game.processCommand(Talk.NAME, "peppa");
        assertTrue(game.getGamePlan().isPerfectWin());
    }

    @Test
    public void isGameEndLoss() {
        game.getGamePlan().setLoss(true);
        String CorrectText = "You did not manage to obtain food for Peppa and yourself in time." +
                "\na Loss, better luck next time.\n";
        assertEquals(CorrectText, game.returnEpilogue());


        game.getProgressInstance().setProgress(3);
        String correctText2 = "Sven got beat by the thief. The thief managed to escape with the stolen clothes." +
                "\na Loss, better luck next time.\n";
        assertEquals(correctText2, game.returnEpilogue());
    }

    @Test
    public void isGameEndWin() {
        game.getGamePlan().setWin(true);
        String correctText = "You managed to obtain food for Peppa and yourself.\n" +
                "Kim will be hungry today - you can do better next time.\n" +
                "a Win, good job.\n";
        assertEquals(correctText, game.returnEpilogue());

    }

    @Test
    public void isGameEndPerfectWin() {
        game.getGamePlan().setPerfectWin(true);
        String correctText = "You managed to obtain food for everyone and Sven bought snus.\n" +
                "a Perfect win, congratulations.\n";
        assertEquals(correctText, game.returnEpilogue());
    }
}