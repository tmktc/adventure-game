package cz.vse.sven.logic.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void basicGameplay() {
        assertEquals("home", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("go soupKitchen");
        assertFalse(game.isGameEnd());
        assertEquals("soupKitchen", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("go junkyard");
        assertFalse(game.isGameEnd());
        assertEquals("junkyard", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("end");
        assertTrue(game.isGameEnd());
    }

    @Test
    public void gameplayLoss1() {
        game.processCommand("go soupKitchen");
        game.processCommand("go junkyard");
        game.processCommand("pickUp oldClock");
        game.processCommand("go pawnshop");
        game.processCommand("talk pawnbroker");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("talk suspect");
        assertTrue(game.getGamePlan().isLoss());
    }

    @Test
    public void gameplayLoss2() {
        // did not manage to buy food in time
        game.processCommand("go soupKitchen");
        game.processCommand("go junkyard");
        game.processCommand("pickUp oldClock");
        game.processCommand("go pawnshop");
        game.processCommand("talk pawnbroker");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("talk kim");
        game.processCommand("go junkyard");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("pickUp redJacket");
        game.processCommand("pickUp greenCap");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("go home");
        game.processCommand("talk peppa");
        assertTrue(game.getGamePlan().isLoss());
    }

    @Test
    public void gameplayWin() {
        game.processCommand("go soupKitchen");
        game.processCommand("go junkyard");
        game.processCommand("pickUp oldClock");
        game.processCommand("go pawnshop");
        game.processCommand("talk pawnbroker");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("talk kim");
        game.processCommand("go junkyard");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("pickUp redJacket");
        game.processCommand("pickUp greenCap");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("go lidl");
        game.processCommand("buy bagels");
        game.processCommand("buy dogFood");
        game.processCommand("go soupKitchen");
        game.processCommand("go home");
        game.processCommand("talk peppa");
        assertTrue(game.getGamePlan().isWin());
    }

    @Test
    public void gameplayPerfectWin() {
        game.processCommand("go soupKitchen");
        game.processCommand("go junkyard");
        game.processCommand("pickUp oldClock");
        game.processCommand("pickUp svijanyBottle");
        game.processCommand("pickUp pilsnerBottle");
        game.processCommand("pickUp branikBottle");
        game.processCommand("go pawnshop");
        game.processCommand("talk pawnbroker");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("talk kim");
        game.processCommand("go junkyard");
        game.processCommand("go jobCenter");
        game.processCommand("talk suspect");
        game.processCommand("pickUp redJacket");
        game.processCommand("pickUp greenCap");
        game.processCommand("go thriftShop");
        game.processCommand("talk shopAssistant");
        game.processCommand("go junkyard");
        game.processCommand("go soupKitchen");
        game.processCommand("go lidl");
        game.processCommand("return svijanyBottle");
        game.processCommand("return pilsnerBottle");
        game.processCommand("return branikBottle");
        game.processCommand("buy bagels");
        game.processCommand("buy dogFood");
        game.processCommand("buy glutenFreeBread");
        game.processCommand("go kiosk");
        game.processCommand("buy snus");
        game.processCommand("go soupKitchen");
        game.processCommand("talk kim");
        game.processCommand("go home");
        game.processCommand("talk peppa");
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