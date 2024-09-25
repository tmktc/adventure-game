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
    public void zakladniPrubehHry() {
        assertEquals("domov", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi jidelna");
        assertFalse(game.gameEnd());
        assertEquals("jidelna", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi smetiste");
        assertFalse(game.gameEnd());
        assertEquals("smetiste", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("konec");
        assertTrue(game.gameEnd());
    }

    /**
     * Otestuje scénář hry, kdy prohrajeme, protože nám uteče lupič
     */
    @Test
    public void prubehHryProhra1() {
        game.processCommand("jdi jidelna");
        game.processCommand("jdi smetiste");
        game.processCommand("seber StareHodiny");
        game.processCommand("jdi zastavarna");
        game.processCommand("promluv Zastavarnik");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("promluv Podezrely");
        assertTrue(game.getGamePlan().isLoss());
    }

    /**
     * Otestuje scénář, kdy prohrajeme, protože jsme nestihli koupit jídlo
     */
    @Test
    public void prubehHryProhra2() {
        game.processCommand("jdi jidelna");
        game.processCommand("jdi smetiste");
        game.processCommand("seber StareHodiny");
        game.processCommand("jdi zastavarna");
        game.processCommand("promluv Zastavarnik");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("promluv Kim");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("seber CervenaBunda");
        game.processCommand("seber ZelenaCepice");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("jdi domov");
        game.processCommand("promluv Pepa");
        assertTrue(game.getGamePlan().isLoss());
    }

    /**
     * Otestujeme scénář výhry
     */
    @Test
    public void prubehHryVyhra() {
        game.processCommand("jdi jidelna");
        game.processCommand("jdi smetiste");
        game.processCommand("seber StareHodiny");
        game.processCommand("jdi zastavarna");
        game.processCommand("promluv Zastavarnik");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("promluv Kim");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("seber CervenaBunda");
        game.processCommand("seber ZelenaCepice");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("jdi lidl");
        game.processCommand("kup Rohliky");
        game.processCommand("kup PsiGranule");
        game.processCommand("jdi jidelna");
        game.processCommand("jdi domov");
        game.processCommand("promluv Pepa");
        assertTrue(game.getGamePlan().isWin());
    }

    /**
     * Otestuje scénář perfektní výhry
     */
    @Test
    public void prubehHryPerfektniVyhra() {
        game.processCommand("jdi jidelna");
        game.processCommand("jdi smetiste");
        game.processCommand("seber StareHodiny");
        game.processCommand("seber LahevOdSvijan");
        game.processCommand("seber LahevOdPlzne");
        game.processCommand("seber LahevOdBranika");
        game.processCommand("jdi zastavarna");
        game.processCommand("promluv Zastavarnik");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("promluv Kim");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi pracak");
        game.processCommand("promluv Podezrely");
        game.processCommand("seber CervenaBunda");
        game.processCommand("seber ZelenaCepice");
        game.processCommand("jdi sekac");
        game.processCommand("promluv Prodavac");
        game.processCommand("jdi smetiste");
        game.processCommand("jdi jidelna");
        game.processCommand("jdi lidl");
        game.processCommand("vymen LahevOdSvijan");
        game.processCommand("vymen LahevOdPlzne");
        game.processCommand("vymen LahevOdBranika");
        game.processCommand("kup Rohliky");
        game.processCommand("kup PsiGranule");
        game.processCommand("kup BezlepkovyChleba");
        game.processCommand("jdi trafika");
        game.processCommand("kup Snus");
        game.processCommand("jdi jidelna");
        game.processCommand("promluv Kim");
        game.processCommand("jdi domov");
        game.processCommand("promluv Pepa");
        assertTrue(game.getGamePlan().isPerfectWin());
    }

    /**
     * Otestuje různé konce hry
     */
    @Test
    public void gameEndProhra() {
        game.getGamePlan().setLoss(true);
        String spravnyText = "Nestihli jste koupit pro jídlo pro sebe a pro Pepu" +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText, game.returnEpilogue());


        game.getProgressInstance().setProgress(3);
        String spravnyText2 = "Sven byl sám na lupiče krátký, lupičovi se podařilo s oblečením utéct." +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText2, game.returnEpilogue());
    }

    @Test
    public void gameEndVyhra() {
        game.getGamePlan().setWin(true);
        String spravnyText = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                "Výhra, dobrá práce.\n";
        assertEquals(spravnyText, game.returnEpilogue());

    }

    @Test
    public void gameEndPerfektniVyhra() {
        game.getGamePlan().setPerfectWin(true);
        String spravnyText = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                "Perfektni výhra, gratuluji.\n";
        assertEquals(spravnyText, game.returnEpilogue());
    }
}