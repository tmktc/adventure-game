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
        assertEquals("domov", game.getHerniPlan().getAktualniProstor().getNazev());
        game.zpracujPrikaz("jdi jidelna");
        assertFalse(game.konecHry());
        assertEquals("jidelna", game.getHerniPlan().getAktualniProstor().getNazev());
        game.zpracujPrikaz("jdi smetiste");
        assertFalse(game.konecHry());
        assertEquals("smetiste", game.getHerniPlan().getAktualniProstor().getNazev());
        game.zpracujPrikaz("konec");
        assertTrue(game.konecHry());
    }

    /**
     * Otestuje scénář hry, kdy prohrajeme, protože nám uteče lupič
     */
    @Test
    public void prubehHryProhra1() {
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("seber StareHodiny");
        game.zpracujPrikaz("jdi zastavarna");
        game.zpracujPrikaz("promluv Zastavarnik");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("promluv Podezrely");
        assertTrue(game.getHerniPlan().isProhra());
    }

    /**
     * Otestuje scénář, kdy prohrajeme, protože jsme nestihli koupit jídlo
     */
    @Test
    public void prubehHryProhra2() {
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("seber StareHodiny");
        game.zpracujPrikaz("jdi zastavarna");
        game.zpracujPrikaz("promluv Zastavarnik");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("promluv Kim");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("seber CervenaBunda");
        game.zpracujPrikaz("seber ZelenaCepice");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi domov");
        game.zpracujPrikaz("promluv Pepa");
        assertTrue(game.getHerniPlan().isProhra());
    }

    /**
     * Otestujeme scénář výhry
     */
    @Test
    public void prubehHryVyhra() {
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("seber StareHodiny");
        game.zpracujPrikaz("jdi zastavarna");
        game.zpracujPrikaz("promluv Zastavarnik");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("promluv Kim");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("seber CervenaBunda");
        game.zpracujPrikaz("seber ZelenaCepice");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi lidl");
        game.zpracujPrikaz("kup Rohliky");
        game.zpracujPrikaz("kup PsiGranule");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi domov");
        game.zpracujPrikaz("promluv Pepa");
        assertTrue(game.getHerniPlan().isVyhra());
    }

    /**
     * Otestuje scénář perfektní výhry
     */
    @Test
    public void prubehHryPerfektniVyhra() {
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("seber StareHodiny");
        game.zpracujPrikaz("seber LahevOdSvijan");
        game.zpracujPrikaz("seber LahevOdPlzne");
        game.zpracujPrikaz("seber LahevOdBranika");
        game.zpracujPrikaz("jdi zastavarna");
        game.zpracujPrikaz("promluv Zastavarnik");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("promluv Kim");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi pracak");
        game.zpracujPrikaz("promluv Podezrely");
        game.zpracujPrikaz("seber CervenaBunda");
        game.zpracujPrikaz("seber ZelenaCepice");
        game.zpracujPrikaz("jdi sekac");
        game.zpracujPrikaz("promluv Prodavac");
        game.zpracujPrikaz("jdi smetiste");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("jdi lidl");
        game.zpracujPrikaz("vymen LahevOdSvijan");
        game.zpracujPrikaz("vymen LahevOdPlzne");
        game.zpracujPrikaz("vymen LahevOdBranika");
        game.zpracujPrikaz("kup Rohliky");
        game.zpracujPrikaz("kup PsiGranule");
        game.zpracujPrikaz("kup BezlepkovyChleba");
        game.zpracujPrikaz("jdi trafika");
        game.zpracujPrikaz("kup Snus");
        game.zpracujPrikaz("jdi jidelna");
        game.zpracujPrikaz("promluv Kim");
        game.zpracujPrikaz("jdi domov");
        game.zpracujPrikaz("promluv Pepa");
        assertTrue(game.getHerniPlan().isPerfektniVyhra());
    }

    /**
     * Otestuje různé konce hry
     */
    @Test
    public void konecHryProhra() {
        game.getHerniPlan().setProhra(true);
        String spravnyText = "Nestihli jste koupit pro jídlo pro sebe a pro Pepu" +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText, game.vratEpilog());


        game.getProgressInstance().setProgress(3);
        String spravnyText2 = "Sven byl sám na lupiče krátký, lupičovi se podařilo s oblečením utéct." +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText2, game.vratEpilog());
    }

    @Test
    public void konecHryVyhra() {
        game.getHerniPlan().setVyhra(true);
        String spravnyText = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                "Výhra, dobrá práce.\n";
        assertEquals(spravnyText, game.vratEpilog());

    }

    @Test
    public void konecHryPerfektniVyhra() {
        game.getHerniPlan().setPerfektniVyhra(true);
        String spravnyText = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                "Perfektni výhra, gratuluji.\n";
        assertEquals(spravnyText, game.vratEpilog());
    }
}