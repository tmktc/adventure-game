package cz.vse.sven.logic.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída HraTest slouží k otestování třídy Hra
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     * Otestuje základní průběh hry
     */
    @Test
    public void basicGameplay() {
        assertEquals("home", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("go soupKitchen");
        assertFalse(game.gameEnd());
        assertEquals("soupKitchen", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("go junkyard");
        assertFalse(game.gameEnd());
        assertEquals("junkyard", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("end");
        assertTrue(game.gameEnd());
    }

    /**
     * Otestuje scénář hry, kdy prohrajeme, protože nám uteče lupič
     */
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

    /**
     * Otestuje scénář, kdy prohrajeme, protože jsme nestihli koupit jídlo
     */
    @Test
    public void gameplayLoss2() {
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
        game.processCommand("talk pepa");
        assertTrue(game.getGamePlan().isLoss());
    }

    /**
     * Otestujeme scénář výhry
     */
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
        game.processCommand("talk pepa");
        assertTrue(game.getGamePlan().isWin());
    }

    /**
     * Otestuje scénář perfektní výhry
     */
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
        game.processCommand("talk pepa");
        assertTrue(game.getGamePlan().isPerfectWin());
    }

    /**
     * Otestuje různé konce hry
     */
    @Test
    public void gameEndLoss() {
        game.getGamePlan().setLoss(true);
        String spravnyText = "You did not manage to obtain food for Pepa and yourself in time." +
                "\na Loss, better luck next time.\n";
        assertEquals(spravnyText, game.returnEpilogue());


        game.getProgressInstance().setProgress(3);
        String spravnyText2 = "Sven got beat by the thief. The thief managed to escape with the stolen clothes." +
                "\na Loss, better luck next time.\n";
        assertEquals(spravnyText2, game.returnEpilogue());
    }

    @Test
    public void gameEndWin() {
        game.getGamePlan().setWin(true);
        String spravnyText = "You managed to obtain food for Pepa and yourself.\n" +
                "Kim will be hungry today - you can do better next time.\n" +
                "a Win, good job.\n";
        assertEquals(spravnyText, game.returnEpilogue());

    }

    @Test
    public void gameEndPerfectWin() {
        game.getGamePlan().setPerfectWin(true);
        String spravnyText = "You managed to obtain food for everyone and Sven bought snus.\n" +
                "a Perfect win, congratulations.\n";
        assertEquals(spravnyText, game.returnEpilogue());
    }
}